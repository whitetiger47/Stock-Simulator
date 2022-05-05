package com.project.stocktrading.service.Stocks.Trending;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.Stocks.Trending.ITrendingRepository;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.service.Stocks.Stocks.StockService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author abhishekuppe
 */
public class TrendingServiceTest {
    private static final int stockId = 3;
    private static final BigDecimal latestStockPrice = new BigDecimal("101.2");
    private static final BigDecimal secondLatestStockPrice = new BigDecimal("121.2");
    private static final IStock latestStock = getStock(stockId, "Meta",
            "APP", latestStockPrice, new Date(), new BigInteger("100"),
            new BigInteger("120"), new BigDecimal("1.1"), new Date());
    private static final IStock secondLatestStock = getStock(stockId, "Google",
            "GOO", secondLatestStockPrice, new Date(), new BigInteger("340"),
            new BigInteger("356"), new BigDecimal("0.1"), new Date());
    private static final IStock stock = getStock(stockId, "Microsoft",
            "MIC", new BigDecimal("134.1"), new Date(), new BigInteger("340"),
            new BigInteger("356"), new BigDecimal("0.1"), new Date());
    private static final ArrayList<IStock> trendingStocks = new ArrayList<>();
    private static ITrendingService trendingService;

    private static IStock getStock(Integer id, String name, String abbreviation, BigDecimal price,
                                   Date initialPublicOffering, BigInteger buyCount, BigInteger sellCount,
                                   BigDecimal difference, Date dateTimeCreated) {
        IStock stock = StockAbstractFactory.getInstance().createNewStock();
        stock.setId(id);
        stock.setName(name);
        stock.setAbbreviation(abbreviation);
        stock.setPrice(price);
        stock.setInitialPublicOffering(initialPublicOffering);
        stock.setBuyCount(buyCount);
        stock.setSellCount(sellCount);
        stock.setDifference(difference);
        stock.setDateTimeCreated(dateTimeCreated);

        return stock;
    }

    @BeforeAll
    public static void init() {
        ITrendingRepository trendingRepository = Mockito.mock(ITrendingRepository.class);
        IStockRepository stockRepository = Mockito.mock(IStockRepository.class);

        Mockito.when(stockRepository.getLatestStockPrice(stockId)).thenReturn(latestStock);
        Mockito.when(stockRepository.getSecondLatestStockPrice(stockId)).thenReturn(secondLatestStock);
        Mockito.when(stockRepository.getStock(stockId)).thenReturn(stock);

        trendingStocks.add(stock);
        trendingStocks.add(latestStock);
        trendingStocks.add(secondLatestStock);

        Mockito.when(trendingRepository.getTrendingStocks()).thenReturn(trendingStocks);

        trendingService = new TrendingService(new StockService(stockRepository), trendingRepository, stockRepository);
    }

    @Test
    public void getTrendingStocksTest() {
        assertEquals(trendingService.getTrendingStocks(), trendingStocks);
    }
}
