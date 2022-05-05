package com.project.stocktrading.dao.Stocks.Trending;

/**
 * @author abhishekuppe
 */
public class TrendingFactory extends TrendingAbstractFactory {
    @Override
    public ITrendingRepository createNewTrendingRepository() {
        return new TrendingRepository();
    }
}