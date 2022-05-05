package com.project.stocktrading.service.Stocks.Chart;

import com.project.stocktrading.models.Stock.IStock;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IChartService {
    ArrayList<IStock> getStockPricesData(int id);
}
