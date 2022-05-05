package com.project.stocktrading.dao.History;

import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.History.IHistory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * @author zhaoling
 */

@Component
public class HistoryRepository implements IHistoryRepository {

    private static final String queryCreateHistoryTable = "CREATE TABLE HISTORY(id int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "value varchar(10000), datetime_created datetime)";
    private static final String queryInsertIntoHistoryTable = "INSERT INTO HISTORY(value, datetime_created) VALUES(?,?)";
    private static final String queryGetHistoryData = "SELECT value, datetime_created FROM HISTORY ORDER BY datetime_created DESC";
    private static final String getStockNameByStockId = "SELECT name from STOCK where id = ?";


    String queryIsTableExists = "SELECT count(*) from information_schema.tables where table_name = ?";

    public int createHistoryTable() {
        Integer countStock = this.getCountStock();
        if (countStock == 0) {
            IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
            Connection connection = iDatabaseUtils.getConnection();

            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;
            try {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                resultSet = databaseMetaData.getTables(null, null, "HISTORY", null);

                if (resultSet.next()) {
                    return 1;
                } else {
                    preparedStatement = connection.prepareStatement(queryCreateHistoryTable);
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
        return 0;
    }

    private Integer getCountStock() {
        Integer countStock = 0;
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(queryIsTableExists);
            preparedStatement.setString(1, "HISTORY");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countStock = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return countStock;
    }

    public String getStockNameByStockId(Integer stock_id) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String name = "";
        try {
            preparedStatement = connection.prepareStatement(getStockNameByStockId);
            preparedStatement.setInt(1, stock_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return name;
    }

    public int addToHistory(IHistory history) {
        createHistoryTable();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryInsertIntoHistoryTable);
            preparedStatement.setString(1, history.getValue());
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public List<Map<String, Object>> getHistoryData() {
        createHistoryTable();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<Map<String, Object>> historys = new ArrayList<Map<String, Object>>();
        try {
            preparedStatement = connection.prepareStatement(queryGetHistoryData);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> history = new HashMap<String, Object>();
                history.put("value", resultSet.getString("value"));
                history.put("datetime_created", resultSet.getTimestamp("datetime_created"));
                historys.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return historys;
    }

}