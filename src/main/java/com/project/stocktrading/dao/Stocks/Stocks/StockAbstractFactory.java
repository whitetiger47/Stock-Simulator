package com.project.stocktrading.dao.Stocks.Stocks;

import com.project.stocktrading.models.Stock.IStock;

/**
 * @author abhishekuppe
 */
public abstract class StockAbstractFactory {

    private static StockAbstractFactory stockAbstractFactory = null;

    public static StockAbstractFactory getInstance() {
        if (stockAbstractFactory == null) {
            stockAbstractFactory = new StockFactory();
        }
        return stockAbstractFactory;
    }

    public abstract IStock createNewStock();

    public abstract IStockRepository createNewStockRepository();
}
