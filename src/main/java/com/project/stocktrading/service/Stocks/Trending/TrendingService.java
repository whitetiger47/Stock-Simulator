package com.project.stocktrading.service.Stocks.Trending;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Trending.ITrendingRepository;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.service.Stocks.Stocks.IStockService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abhishekuppe
 */
@Service
public class TrendingService implements ITrendingService {

    private final ITrendingRepository trendingRepository;
    private final IStockRepository stockRepository;
    private final IStockService stockService;

    public TrendingService(IStockService stockService, ITrendingRepository trendingRepository, IStockRepository stockRepository) {
        this.trendingRepository = trendingRepository;
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    public List<IStock> getTrendingStocks() {
        List<IStock> stocks = this.trendingRepository.getTrendingStocks();
        for (IStock stock : stocks) {
            if (this.stockService.getDifferenceOfStockValue(stock.getId()) == null) {
                continue;
            }
            stock.setDifference(this.stockService.getDifferenceOfStockValue(stock.getId()).getDifference());
            stock.setPrice(this.stockRepository.getLatestStockPrice(stock.getId()).getPrice());
        }
        return stocks;
    }
}
