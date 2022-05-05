package com.project.stocktrading.dao.Stocks.Chart;


/**
 * @author abhishekuppe
 */
public abstract class ChartAbstractFactory {
    private static ChartAbstractFactory chartAbstractFactory = null;

    public static ChartAbstractFactory getInstance() {
        if (chartAbstractFactory == null) {
            chartAbstractFactory = new ChartFactory();
        }
        return chartAbstractFactory;
    }

    public abstract IChartRepository createNewChartRepository();
}
