package com.project.stocktrading.dao.Stocks.Trending;

import com.project.stocktrading.models.Stock.IStock;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface ITrendingRepository {
    ArrayList<IStock> getTrendingStocks();
}
