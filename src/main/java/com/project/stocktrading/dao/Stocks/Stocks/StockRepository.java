package com.project.stocktrading.dao.Stocks.Stocks;

import com.project.stocktrading.dao.Stocks.NewStock;
import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author abhishekuppe
 */

@Component
public class StockRepository extends NewStock implements IStockRepository {

    private static final String queryGetStock = "SELECT * from STOCK where id=?";
    private static final String queryGetAllStocks = "SELECT * from STOCK";
    private static final String queryGetLatestStockPrice = "SELECT price FROM STOCK_PRICE WHERE stock_id = ? " +
            "ORDER BY datetime_created DESC LIMIT 1";

    private static final String queryGetSecondLatestStockPrice = "SELECT price FROM STOCK_PRICE WHERE stock_id = ? " +
            "ORDER BY datetime_created DESC LIMIT 1,1";

    private final String queryInsertIntoStockPriceTable = "INSERT INTO STOCK_PRICE (stock_id, price, datetime_created)" +
            " VALUES (?,?,?)";


    public ArrayList<IStock> getAllStocks() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<IStock> newStockList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(queryGetAllStocks);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                newStockList.add(this.getNewStock(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }

        return newStockList;
    }

    public int addNewStockPrice(int stockId, BigDecimal price) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryInsertIntoStockPriceTable);
            preparedStatement.setInt(1, stockId);
            preparedStatement.setBigDecimal(2, price);
            preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    public IStock getStock(Integer id) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        IStock iStock = null;

        try {
            preparedStatement = connection.prepareStatement(queryGetStock);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                iStock = this.getNewStock(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return iStock;
    }


    private IStock getStockPrice(int stockId, String query) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        IStock iStock = StockAbstractFactory.getInstance().createNewStock();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, stockId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                try {
                    iStock.setPrice(resultSet.getBigDecimal("price"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return iStock;
    }

    public IStock getLatestStockPrice(int stockId) {
        return getStockPrice(stockId, queryGetLatestStockPrice);
    }

    public IStock getSecondLatestStockPrice(int stockId) {
        return getStockPrice(stockId, queryGetSecondLatestStockPrice);
    }
}
