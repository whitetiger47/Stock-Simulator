package com.project.stocktrading.dao.Stocks.Trending;

/**
 * @author abhishekuppe
 */
public abstract class TrendingAbstractFactory {
    private static TrendingAbstractFactory trendingAbstractFactory = null;

    public static TrendingAbstractFactory getInstance() {
        if (trendingAbstractFactory == null) {
            trendingAbstractFactory = new TrendingFactory();
        }
        return trendingAbstractFactory;
    }

    public abstract ITrendingRepository createNewTrendingRepository();
}
