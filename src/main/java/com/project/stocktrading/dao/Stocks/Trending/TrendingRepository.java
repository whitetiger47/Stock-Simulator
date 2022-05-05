package com.project.stocktrading.dao.Stocks.Trending;

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
public class TrendingRepository extends NewStock implements ITrendingRepository {

    private static final String queryGetTrendingStocks = "SELECT * from STOCK ORDER BY buy_count DESC, sell_count ASC";

    public ArrayList<IStock> getTrendingStocks() {
        ArrayList<IStock> stocks = new ArrayList<>();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();

        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryGetTrendingStocks);
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
