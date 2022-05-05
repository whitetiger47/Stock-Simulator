package com.project.stocktrading.service.Stocks.Stocks;

import com.project.stocktrading.models.Stock.IStock;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IStockService {
    double getRandomDoubleInRange(int min, int max);

    int getRandomIntegerInRange(int min, int max);

    BigDecimal generateStockPrice(BigDecimal price);

    ArrayList<BigDecimal> generateStockPrices(ArrayList<IStock> stocks);

    boolean runInBackground();

    ArrayList<IStock> getStock();

    IStock getLatestStockPrice(int stockId);

    IStock getDifferenceOfStockValue(int stockId);
}
