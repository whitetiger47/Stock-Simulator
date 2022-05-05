package com.project.stocktrading.dao.Basket;

import com.project.stocktrading.models.Basket.IBasket;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author abhishekuppe
 */
public abstract class NewBasket {
    protected IBasket getNewBasket(ResultSet resultSet) {
        IBasket basket = BasketAbstractFactory.getInstance().createNewBasket();
        try {
            basket.setId(resultSet.getInt("id"));
            basket.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return basket;
    }
}
