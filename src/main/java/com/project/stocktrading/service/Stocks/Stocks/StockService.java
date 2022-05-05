package com.project.stocktrading.service.Stocks.Stocks;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author abhishekuppe
 */

@Service
public class StockService implements IStockService {

    private final IStockRepository stockRepository;

    private final int STOCK_GENERATION_DURATION = 10000 * 100;
    private final int INITIAL_DELAY = 1000;

    public StockService(IStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public double getRandomDoubleInRange(int min, int max) {
        return min + (max - min) * new Random().nextDouble();
    }

    public int getRandomIntegerInRange(int min, int max) {
        return min + new Random().nextInt(max - min);
    }

    public BigDecimal generateStockPrice(BigDecimal price) {
        int doubleMin = 1;
        int doubleMax = 10;

        double value = getRandomDoubleInRange(doubleMin, doubleMax) / doubleMax;


        int intMin = 1;
        int intMax = 10;

        // Giving more weightage to increase the stock
        if (getRandomIntegerInRange(intMin, intMax) < 9) {
            return new BigDecimal(String.valueOf(price)).add(new BigDecimal(value));
        }

        // decrease the stock price
        return new BigDecimal(String.valueOf(price)).subtract(new BigDecimal(value));
    }

    public ArrayList<BigDecimal> generateStockPrices(ArrayList<IStock> stocks) {
        ArrayList<BigDecimal> bigDecimals = new ArrayList<>();
        for (IStock stock : stocks) {
            Integer stockId = stock.getId();
            IStock latestStock = this.stockRepository.getLatestStockPrice(stockId);
            BigDecimal stockPrice = generateStockPrice(latestStock.getPrice());
            bigDecimals.add(stockPrice);
            this.stockRepository.addNewStockPrice(stockId, stockPrice);
        }
        return bigDecimals;
    }

    public boolean runInBackground() {
        ArrayList<IStock> stocks = this.stockRepository.getAllStocks();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generateStockPrices(stocks);
            }
        }, INITIAL_DELAY, STOCK_GENERATION_DURATION);
        return true;
    }

    public ArrayList<IStock> getStock() {
        ArrayList<IStock> stocks = this.stockRepository.getAllStocks();
        return stocks;
    }

    public IStock getLatestStockPrice(int stockId) {
        return this.stockRepository.getLatestStockPrice(stockId);
    }

    public IStock getDifferenceOfStockValue(int stockId) {
        BigDecimal bigDecimal = this.stockRepository.getLatestStockPrice(stockId).getPrice().
                subtract(this.stockRepository.getSecondLatestStockPrice(stockId).getPrice());
        this.stockRepository.getSecondLatestStockPrice(stockId).setDifference(bigDecimal.divide(BigDecimal.valueOf(100)));
        IStock stock = this.stockRepository.getStock(stockId);
        stock.setDifference(bigDecimal.divide(BigDecimal.valueOf(100)));
        return stock;
    }
}