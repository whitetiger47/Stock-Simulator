package com.project.stocktrading.service.Stocks.Stocks;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.Stocks.Stocks.StockRepository;
import com.project.stocktrading.models.Stock.IStock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author abhishekuppe
 */
public class StockServiceTest {

    private static final int minIntRange = 1;
    private static final int maxIntRange = 10;
    private static final double minDoubleRange = 1.0;
    private static final double maxDoubleRange = 12.12;
    private static final BigDecimal latestStockPrice = new BigDecimal("101.2");
    private static final BigDecimal secondLatestStockPrice = new BigDecimal("121.2");

    private static final int latestStockId = 1;
    private static final int secondLatestStockId = 2;
    private static final int stockId = 3;

    private static final ArrayList<IStock> stocks = new ArrayList<>();
    private static final IStock latestStock = getStock(latestStockId, "Apple",
            "APP", latestStockPrice, new Date(), new BigInteger("100"),
            new BigInteger("120"), new BigDecimal("1.1"), new Date());
    private static final IStock secondLatestStock = getStock(secondLatestStockId, "Google",
            "GOO", secondLatestStockPrice, new Date(), new BigInteger("340"),
            new BigInteger("356"), new BigDecimal("0.1"), new Date());
    private static final IStock stock = getStock(stockId, "Microsoft",
            "MIC", new BigDecimal("134.1"), new Date(), new BigInteger("340"),
            new BigInteger("356"), new BigDecimal("0.1"), new Date());
    private static IStockService stockService;

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
        IStockRepository stockRepository = Mockito.mock(StockRepository.class);

        Mockito.when(stockRepository.getLatestStockPrice(stockId)).thenReturn(latestStock);
        Mockito.when(stockRepository.getLatestStockPrice(latestStockId)).thenReturn(latestStock);
        Mockito.when(stockRepository.getLatestStockPrice(secondLatestStockId)).thenReturn(secondLatestStock);

        Mockito.when(stockRepository.getSecondLatestStockPrice(stockId)).thenReturn(secondLatestStock);

        Mockito.when(stockRepository.addNewStockPrice(stockId, latestStockPrice)).thenReturn(1);

        Mockito.when(stockRepository.getStock(stockId)).thenReturn(stock);

        stocks.add(getStock(1, "Apple",
                "APP", new BigDecimal("101.1"), new Date(), new BigInteger("100"),
                new BigInteger("120"), new BigDecimal("1.1"), new Date()));
        stocks.add(getStock(2, "Google",
                "GOO", new BigDecimal("134.1"), new Date(), new BigInteger("340"),
                new BigInteger("356"), new BigDecimal("0.1"), new Date()));

        Mockito.when(stockRepository.getAllStocks()).thenReturn(stocks);

        stockService = new StockService(stockRepository);
    }

    @Test
    public void getRandomIntegerInRangeTest1() {
        assertTrue(maxIntRange >= stockService.getRandomIntegerInRange(minIntRange, maxIntRange),
                "Error, random is too high");
    }

    @Test
    public void getRandomIntegerInRangeTest2() {
        assertTrue(minIntRange <= stockService.getRandomIntegerInRange(minIntRange, maxIntRange),
                "Error, random is too low");
    }

    @Test
    public void getRandomDoubleInRangeTest1() {
        assertTrue(maxDoubleRange >= stockService.getRandomDoubleInRange(minIntRange, maxIntRange),
                "Error, random is too high");
    }

    @Test
    public void getRandomDoubleInRangeTest2() {
        assertTrue(minDoubleRange <= stockService.getRandomDoubleInRange(minIntRange, maxIntRange),
                "Error, random is too low");
    }

    @Test
    public void generateStockPriceTest1() {
        assertTrue(latestStockPrice.add(BigDecimal.valueOf(1)).compareTo(stockService.generateStockPrice(latestStockPrice)) > 0);
    }

    @Test
    public void generateStockPriceTest2() {
        assertTrue(latestStockPrice.subtract(BigDecimal.valueOf(1)).compareTo(stockService.generateStockPrice(latestStockPrice)) < 0);
    }

    @Test
    public void generateStockPricesTest() {
        ArrayList<BigDecimal> bigDecimals = new ArrayList<>();
        bigDecimals.add(latestStock.getPrice());
        ArrayList<BigDecimal> latestStockPrice = stockService.generateStockPrices(stocks);
        assertTrue(latestStockPrice.get(0).compareTo(bigDecimals.get(0)) != 0);
    }

    @Test
    public void getStockTest() {
        assertEquals(stockService.getStock(), stocks);
    }

    @Test
    public void getLatestStockPriceTest() {
        assertEquals(stockService.getLatestStockPrice(stockId), latestStock);
    }

    @Test
    public void getDifferenceOfStockValueTest() {
        assertEquals(stockService.getDifferenceOfStockValue(stockId), stock);
    }

    @Test
    public void runInBackgroundTest() {
        assertTrue(stockService.runInBackground());
    }
}
