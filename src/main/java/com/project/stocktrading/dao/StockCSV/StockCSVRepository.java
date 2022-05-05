package com.project.stocktrading.dao.StockCSV;

import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.User.IUser;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Date;

/**
 * @author abhishekuppe
 */

@Component
public class StockCSVRepository implements IStockCSVRepository {

    // stock
    private static final String queryCreateStockTable = "CREATE TABLE STOCK (id int NOT NULL PRIMARY KEY, name varchar(100) NOT NULL," +
            " abbreviation varchar(20), initial_public_offering_date datetime, buy_count BIGINT DEFAULT 0," +
            " sell_count BIGINT DEFAULT 0, user_id INT, FOREIGN KEY(user_id) REFERENCES USER(id))";

    private static final String queryInsertIntoStockTable = "INSERT INTO STOCK (id, name, abbreviation, " +
            "initial_public_offering_date, user_id) VALUES (?,?,?,?,?)";

    private static final String queryDeleteStockTable = "DELETE FROM STOCK";

    // stock price
    private static final String queryCreateStockPriceTable = "CREATE TABLE STOCK_PRICE " +
            "(id int NOT NULL AUTO_INCREMENT, stock_id int NOT NULL , price DECIMAL(65, 30) NOT NULL," +
            " datetime_created datetime, PRIMARY KEY (id), FOREIGN KEY(stock_id) REFERENCES STOCK(id), " +
            "user_id INT, FOREIGN KEY(user_id) REFERENCES USER(id))";

    private static final String queryDeleteStockPriceTable = "DELETE FROM STOCK_PRICE";

    private final String queryInsertIntoStockPriceTable = "INSERT INTO STOCK_PRICE (stock_id, price, datetime_created, user_id)" +
            " VALUES (?,?,?,?)";


    public int deleteStockTableEntries() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryDeleteStockTable);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public int deleteStockTablePriceEntries() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryDeleteStockPriceTable);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public int createTable(String query, String name) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            resultSet = databaseMetaData.getTables(null, null, name, null);

            if (resultSet.next()) {
                return 1;
            } else {
                preparedStatement = connection.prepareStatement(query);
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

    public int createStockPriceTable() {
        return createTable(queryCreateStockPriceTable, "STOCK_PRICE");
    }

    public int createStockTable() {
        return createTable(queryCreateStockTable, "STOCK");
    }


    public int insertIntoStockPriceTable(IStock stock, IUser user) {

        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryInsertIntoStockPriceTable);
            preparedStatement.setInt(1, stock.getId());
            preparedStatement.setBigDecimal(2, stock.getPrice());
            preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
            preparedStatement.setInt(4, user.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public int insertIntoStockTable(IStock stock, IUser user) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryInsertIntoStockTable);
            preparedStatement.setInt(1, stock.getId());
            preparedStatement.setString(2, stock.getName());
            preparedStatement.setString(3, stock.getAbbreviation());
            preparedStatement.setDate(4, new java.sql.Date(stock.getInitialPublicOffering().getTime()));
            preparedStatement.setInt(5, user.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }
}
