package com.project.stocktrading.dao.Basket;

import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.User.IUser;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
@Component
public class BasketRepository extends NewBasket implements IBasketRepository {

    private static final String queryCreateBasketTable = "CREATE TABLE BASKET(id int NOT NULL AUTO_INCREMENT, " +
            "name varchar(100) NOT NULL, PRIMARY KEY(id), user_id INT, FOREIGN KEY(user_id) REFERENCES USER(id))";

    private static final String queryCreateBasketDataTable = "CREATE TABLE BASKET_DATA(basket_id int NOT NULL," +
            "stock_id int NOT NULL, PRIMARY KEY(basket_id, stock_id), FOREIGN KEY(basket_id) REFERENCES BASKET(id), " +
            "FOREIGN KEY(stock_id) REFERENCES STOCK(id))";

    private static final String queryInsertIntoBasket = "INSERT INTO BASKET (name, user_id) VALUES (?,?)";

    private static final String queryInsertIntoBasketData = "INSERT INTO BASKET_DATA (basket_id, stock_id) " +
            "VALUES(?, ?)";

    private static final String queryGetLastInsertedId = "SELECT MAX(id) FROM BASKET where user_id = ?";

    private static final String queryGetBaskets = "SELECT * FROM BASKET where user_id = ?";

    private static final String queryGetStockIdsFromBasket = "SELECT stock_id FROM BASKET_DATA where basket_id = ? ORDER BY stock_id DESC";


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

    public int createBasketTable() {
        return createTable("BASKET", queryCreateBasketTable);
    }

    public int createBasketDataTable() {
        return createTable("BASKET_DATA", queryCreateBasketDataTable);
    }

    public int createBasketWithStocks(IBasket basket, ArrayList<Integer> stockIds, IUser user) {

        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(queryInsertIntoBasket);
            preparedStatement.setString(1, basket.getName());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(queryGetLastInsertedId);
            preparedStatement.setInt(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            for (Integer stockId : stockIds) {
                try {
                    preparedStatement = connection.prepareStatement(queryInsertIntoBasketData);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, stockId);
                    preparedStatement.executeUpdate();
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return 1;
    }

    public ArrayList<IBasket> getBaskets(IUser user) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<IBasket> newBasketList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryGetBaskets);
            try {
                preparedStatement.setInt(1, user.getId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    newBasketList.add(this.getNewBasket(resultSet));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return newBasketList;
    }

    public IBasket getStockIdsForBasket(int basketId) {
        IBasket newBasket = BasketAbstractFactory.getInstance().createNewBasket();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(queryGetStockIdsFromBasket);
            preparedStatement.setInt(1, basketId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                try {
                    arrayList.add(resultSet.getInt("stock_id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }

        newBasket.setStock_ids(arrayList);
        return newBasket;
    }
}
