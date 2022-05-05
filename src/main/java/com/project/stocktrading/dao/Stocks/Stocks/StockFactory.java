package com.project.stocktrading.dao.Stocks.Stocks;

import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.Stock.Stock;

/**
 * @author abhishekuppe
 */
public class StockFactory extends StockAbstractFactory {

    @Override
    public IStock createNewStock() {
        return new Stock();
    }

    @Override
    public IStockRepository createNewStockRepository() {
        return new StockRepository();
    }
}
