package com.project.stocktrading.dao.Stocks.Stocks;

import com.project.stocktrading.models.Stock.IStock;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IStockRepository {

    ArrayList<IStock> getAllStocks();

    int addNewStockPrice(int stockId, BigDecimal price);

    IStock getLatestStockPrice(int stockId);

    IStock getStock(Integer id);

    IStock getSecondLatestStockPrice(int stockId);
}
