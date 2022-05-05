package com.project.stocktrading.dao.Stocks.Chart;

import com.project.stocktrading.models.Stock.IStock;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IChartRepository {
    ArrayList<IStock> getStockPrices(int stockId);
}
