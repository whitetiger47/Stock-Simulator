package com.project.stocktrading.service.Stocks.Trending;

import com.project.stocktrading.models.Stock.IStock;

import java.util.List;

/**
 * @author abhishekuppe
 */
public interface ITrendingService {
    List<IStock> getTrendingStocks();
}
