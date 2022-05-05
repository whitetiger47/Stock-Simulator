package com.project.stocktrading.service.Stocks.Chart;

import com.project.stocktrading.dao.Stocks.Chart.IChartRepository;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */

@Service
public class ChartService implements IChartService {

    IChartRepository chartRepository;

    public ChartService(IChartRepository iChartRepository) {
        this.chartRepository = iChartRepository;
    }

    public ArrayList<IStock> getStockPricesData(int id) {
        return this.chartRepository.getStockPrices(id);
    }
}
