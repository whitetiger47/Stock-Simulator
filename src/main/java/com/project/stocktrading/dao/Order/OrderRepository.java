package com.project.stocktrading.dao.Order;

import com.project.stocktrading.database.DatabaseAbstractFactory;
import com.project.stocktrading.database.IDatabaseUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoling
 */

@Component
public class OrderRepository implements IOrderRepository {

    private static final String getPendingOrders = "SELECT * from BUY_SELL where is_limit_market = ?";
    private static final String getCompletedOrders = "SELECT * from BUY_SELL where is_limit_market = ?";
    private static final String getStockNameByStockId = "SELECT name from STOCK where id = ?";
    private static final String deleteBuySell = "DELETE from BUY_SELL where stock_id = ? and price = ? and is_buy_sell = ? and is_regular_intraday = ? and is_limit_market = ? and quantity = ?";

    public ArrayList<Map<String, Object>> getPendingOrders() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Map<String, Object>> orders = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(getPendingOrders);
            preparedStatement.setString(1, "limit");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(this.transferMapToOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return orders;
    }

    public ArrayList<Map<String, Object>> getCompletedOrders() {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Map<String, Object>> orders = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(getCompletedOrders);
            preparedStatement.setString(1, "market");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(this.transferMapToOrder(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, resultSet);
        }
        return orders;
    }

    public int deletePendingOrder(Map<String, Object> buySell) {
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(deleteBuySell);
            preparedStatement.setInt(1, Integer.valueOf((String) buySell.get("stock_id")));
            preparedStatement.setBigDecimal(2, new BigDecimal((String) buySell.get("price")));
            preparedStatement.setInt(3, (Integer) buySell.get("type"));
            preparedStatement.setString(4, (String) buySell.get("mode"));
            preparedStatement.setString(5, "limit");
            preparedStatement.setInt(6, Integer.valueOf((String) buySell.get("quantity")));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseAbstractFactory.getInstance().closeConnections(connection, iDatabaseUtils, preparedStatement, null);
        }
        return 0;
    }

    private Map<String, Object> transferMapToOrder(ResultSet resultSet) {
        Map<String, Object> order = new HashMap<String, Object>();
        IDatabaseUtils iDatabaseUtils = DatabaseAbstractFactory.getInstance().createDatabaseUtils();
        Connection connection = iDatabaseUtils.getConnection();
        ResultSet resultSet2 = null;
        PreparedStatement preparedStatement = null;
        String name = "";
        try {
            Integer stock_id = resultSet.getInt("stock_id");
            preparedStatement = connection.prepareStatement(getStockNameByStockId);
            preparedStatement.setInt(1, stock_id);
            resultSet2 = preparedStatement.executeQuery();
            while (resultSet2.next()) {
                name = resultSet2.getString("name");
            }
            order.put("stock_id", stock_id);
            order.put("name", name);
            order.put("price", resultSet.getBigDecimal("price"));
            order.put("quantity", resultSet.getInt("quantity"));
            if (resultSet.getInt("is_buy_sell") == 1)
                order.put("type", "buy");
            else
                order.put("type", "sell");
            order.put("mode", resultSet.getString("is_regular_intraday"));
            order.put("status", resultSet.getString("is_limit_market"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

}
