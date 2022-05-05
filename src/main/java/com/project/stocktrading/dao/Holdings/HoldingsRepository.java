package com.project.stocktrading.dao.Holdings;

import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */

@Component
public class HoldingsRepository implements IHoldingsRepository {

    private static final String getAllHoldings = "SELECT * from STOCK";

    public List<Map<String, Object>> getAllHoldingsFromDb() {
        List<Map<String, Object>> allHoldings = new ArrayList<>();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(getAllHoldings);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Map<String, Object> holding = new HashMap<String, Object>();
                holding.put("stock_id", resultSet.getInt("id"));
                holding.put("name", resultSet.getString("name"));
                holding.put("quantity", resultSet.getBigDecimal("quantity"));
                allHoldings.add(holding);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return allHoldings;
    }
}
