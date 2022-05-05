package com.project.stocktrading.dao.Watchlist;

import com.project.stocktrading.models.BuySell.IBuySell;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewBuySell {
    protected IBuySell getNewBuySell(ResultSet resultSet) throws SQLException {
        IBuySell buySell = WatchlistAbstractFactory.getInstance().createNewBuySell();
        try {
            buySell.setId(resultSet.getInt("id"));
            buySell.setIs_buy_sell(resultSet.getInt("is_buy_sell"));
            buySell.setIs_regular_intraday(resultSet.getString("is_regular_intraday"));
            buySell.setIs_limit_market(resultSet.getString("is_limit_market"));
            buySell.setTarget(resultSet.getFloat("target"));
            buySell.setPrice(resultSet.getBigDecimal("price"));
            buySell.setStoploss(resultSet.getFloat("stoploss"));
            buySell.setQuantity(resultSet.getInt("quantity"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buySell;
    }
}

