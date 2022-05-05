package com.project.stocktrading.dao.Stocks.Chart;

/**
 * @author abhishekuppe
 */
public class ChartFactory extends ChartAbstractFactory {
    @Override
    public IChartRepository createNewChartRepository() {
        return new ChartRepository();
    }
}
