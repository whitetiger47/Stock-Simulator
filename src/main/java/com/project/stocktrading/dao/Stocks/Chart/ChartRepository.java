package com.project.stocktrading.dao.Stocks.Chart;

import com.project.stocktrading.dao.Stocks.NewStock;
import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */

@Component
public class ChartRepository extends NewStock implements IChartRepository {
    private static final String queryGetStockPrices = "SELECT id, price, " +
            "datetime_created from STOCK_PRICE where stock_id = ?";

    public ArrayList<IStock> getStockPrices(int stockId) {
        ArrayList<IStock> stocks = new ArrayList<>();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryGetStockPrices);
            preparedStatement.setInt(1, stockId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stocks.add(getNewStock(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return stocks;
    }
}
