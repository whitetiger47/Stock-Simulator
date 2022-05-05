package com.project.stocktrading.dao.SIP;

import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.models.User.IUser;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */

@Component
public class SIPRepository extends NewSIP implements ISIPRepository {

    private static final String queryCreateSIPTable = "CREATE TABLE SIP(id int NOT NULL AUTO_INCREMENT, " +
            "name varchar(100) NOT NULL, month_schedule tinyint NOT NULL, PRIMARY KEY(id), " +
            "user_id INT, FOREIGN KEY(user_id) REFERENCES USER(id), sip_purchased_month int DEFAULT 1000)";

    private static final String queryCreateSIPDataTable = "CREATE TABLE SIP_DATA(sip_id int NOT NULL," +
            "basket_id int NOT NULL, PRIMARY KEY(sip_id, basket_id), FOREIGN KEY(sip_id) REFERENCES SIP(id))";

    private static final String queryUpdateSIPPurchasedMonth = "UPDATE SIP SET sip_purchased_month= ? where id = ?";

    private static final String queryInsertIntoSIP = "INSERT INTO SIP (name, month_schedule, user_id) VALUES (?, ?, ?)";

    private static final String queryInsertIntoSIPData = "INSERT INTO SIP_DATA (sip_id, basket_id) " +
            "VALUES (?, ?)";

    private static final String queryGetLastInsertedId = "SELECT MAX(id) FROM SIP where user_id = ?";

    private static final String queryGetSIPS = "SELECT * FROM SIP where user_id = ?";

    private static final String queryGetBasketIdsFromSIP = "SELECT basket_id FROM SIP_DATA where sip_id = ? " +
            "ORDER BY basket_id DESC";

    private static final String queryDeleteSIPData = "DELETE FROM SIP_DATA where sip_id = ?";
    private static final String queryDeleteSIP = "DELETE FROM SIP where id = ?";

    public int createTable(String name, String createTableQuery) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            resultSet = databaseMetaData.getTables(null, null, name, null);

            if (resultSet.next()) {
                return 1;
            } else {
                preparedStatement = connection.prepareStatement(createTableQuery);
                preparedStatement.execute();
            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
    }

    public int createSIPTable() {
        return createTable("SIP", queryCreateSIPTable);
    }

    public int createSIPDataTable() {
        return createTable("SIP_DATA", queryCreateSIPDataTable);
    }

    public int updateSIPPurchasedMonth(int month, int id) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryUpdateSIPPurchasedMonth);
            preparedStatement.setInt(1, month);
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public int createSIPWithBaskets(ISIP sip, ArrayList<Integer> basketIds, IUser user) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(queryInsertIntoSIP);
            preparedStatement.setString(1, sip.getName());
            preparedStatement.setInt(2, sip.getMonth_schedule());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryGetLastInsertedId);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            for (Integer basketId : basketIds) {
                try {
                    preparedStatement = connection.prepareStatement(queryInsertIntoSIPData);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, basketId);
                    return preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return 0;
    }


    public String joinString(Object[] array) {
        if (array == null || array.length == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder(1000);
        stringBuilder.append(array[0]);

        for (int i = 1; i < array.length; i++) {
            stringBuilder.append(",").append(array[i]);
        }

        return stringBuilder.toString();
    }

    public ArrayList<ISIP> getSIPs(IUser user) {

        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        ResultSet resultSetBaskets = null;

        PreparedStatement preparedStatement = null;
        PreparedStatement resultSetPreparedStatement = null;

        ArrayList<ISIP> newSIPList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryGetSIPS);
            try {
                preparedStatement.setInt(1, user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    resultSetPreparedStatement = connection.prepareStatement(queryGetBasketIdsFromSIP);
                    try {
                        resultSetPreparedStatement.setInt(1, resultSet.getInt("id"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        resultSetBaskets = resultSetPreparedStatement.executeQuery();
                        ArrayList<Integer> basketList = new ArrayList<>();

                        while (resultSetBaskets.next()) {
                            basketList.add(resultSetBaskets.getInt("basket_id"));
                        }

                        newSIPList.add(this.getNewSIP(resultSet, joinString(basketList.toArray())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            iDatabaseUtils.closeResultSet(resultSetBaskets);
            iDatabaseUtils.closeStatement(resultSetPreparedStatement);
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return newSIPList;
    }

    public ISIP getBasketIdsForSIP(int sipId) {
        ISIP iSip = SIPAbstractFactory.getInstance().createNewSIP();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryGetBasketIdsFromSIP);
            preparedStatement.setInt(1, sipId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                try {
                    arrayList.add(resultSet.getInt("basket_id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            iSip.setBasket_ids(joinString(arrayList.toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return iSip;
    }

    public int deleteSIPData(int sipId) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryDeleteSIPData);
            preparedStatement.setInt(1, sipId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public int deleteSIP(int sipId) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryDeleteSIP);
            preparedStatement.setInt(1, sipId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }
}
