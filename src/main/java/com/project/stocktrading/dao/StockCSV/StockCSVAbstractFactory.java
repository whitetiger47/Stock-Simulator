package com.project.stocktrading.dao.StockCSV;

import com.project.stocktrading.models.StockCSVFile.IStockCSVFile;

/**
 * @author abhishekuppe
 */
public abstract class StockCSVAbstractFactory {
    private static StockCSVAbstractFactory stockCSVAbstractFactory = null;

    public static StockCSVAbstractFactory getInstance() {
        if (stockCSVAbstractFactory == null) {
            stockCSVAbstractFactory = new StockCSVFactory();
        }
        return stockCSVAbstractFactory;
    }

    public abstract IStockCSVRepository createNewStockCSVRepository();

    public abstract IStockCSVFile createNewStockCSV();
}
