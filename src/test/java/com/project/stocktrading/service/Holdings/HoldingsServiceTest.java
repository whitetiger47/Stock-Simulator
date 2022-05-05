package com.project.stocktrading.service.Holdings;

import com.project.stocktrading.dao.Holdings.HoldingsRepository;
import com.project.stocktrading.dao.Holdings.IHoldingsRepository;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockRepository;
import com.project.stocktrading.models.Stock.Stock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author zhaoling
 */
public class HoldingsServiceTest {

    private static IHoldingsService holdingsService;

    @BeforeAll
    public static void init() {
        IHoldingsRepository holdingsRepository = Mockito.mock(HoldingsRepository.class);
        IStockRepository stockRepository = Mockito.mock(StockRepository.class);

        holdingsService = new HoldingsService(holdingsRepository, stockRepository);

        List<Map<String, Object>> holdings = new ArrayList<Map<String, Object>>();
        Map<String, Object> holding1 = new HashMap<String, Object>();
        holding1.put("stock_id", 1);
        holding1.put("name", "Shopify");
        holding1.put("quantity", 10);
        holdings.add(holding1);
        Map<String, Object> holding2 = new HashMap<String, Object>();
        holding2.put("stock_id", 2);
        holding2.put("name", "Royal Bank of Canada");
        holding2.put("quantity", 5);
        holdings.add(holding2);

        Stock stock1 = new Stock();
        stock1.setPrice(new BigDecimal("11"));
        Stock stock2 = new Stock();
        stock2.setPrice(new BigDecimal("22"));

        Mockito.when(holdingsRepository.getAllHoldingsFromDb()).thenReturn(holdings);
        Mockito.when(stockRepository.getLatestStockPrice(1)).thenReturn(stock1);
        Mockito.when(stockRepository.getLatestStockPrice(2)).thenReturn(stock2);

    }

    @Test
    public void testGetAllHoldingsFromDb() {
        List<Map<String, Object>> expectResult = new ArrayList<Map<String, Object>>();
        Map<String, Object> holding1 = new HashMap<String, Object>();
        holding1.put("stock_id", 1);
        holding1.put("name", "Shopify");
        holding1.put("quantity", 10);
        holding1.put("price", new BigDecimal("11"));
        expectResult.add(holding1);
        Map<String, Object> holding2 = new HashMap<String, Object>();
        holding2.put("stock_id", 2);
        holding2.put("name", "Royal Bank of Canada");
        holding2.put("quantity", 5);
        holding2.put("price", new BigDecimal("22"));
        expectResult.add(holding2);
        List<Map<String, Object>> testResult = holdingsService.getAllHoldingsFromDb();

        assertEquals(expectResult, testResult);
    }
}
