package com.project.stocktrading.dao.Stocks;

import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.models.Stock.IStock;

import java.math.BigInteger;
import java.sql.ResultSet;

/**
 * @author abhishekuppe
 */
public abstract class NewStock {
    protected IStock getNewStock(ResultSet resultSet) {
        IStock stock = StockAbstractFactory.getInstance().createNewStock();
        try {
            stock.setId(resultSet.getInt("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setName(resultSet.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setAbbreviation(resultSet.getString("abbreviation"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setPrice(resultSet.getBigDecimal("price"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setDateTimeCreated(resultSet.getDate("dateTimeCreated"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setInitialPublicOffering(resultSet.getDate("initial_public_offering"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setBuyCount(resultSet.getObject("buy_count", BigInteger.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stock.setSellCount(resultSet.getObject("sell_count", BigInteger.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stock;
    }
}
