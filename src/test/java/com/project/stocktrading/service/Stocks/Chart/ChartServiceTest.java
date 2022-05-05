package com.project.stocktrading.service.Stocks.Chart;

import com.project.stocktrading.dao.Stocks.Chart.ChartRepository;
import com.project.stocktrading.dao.Stocks.Chart.IChartRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.models.Stock.IStock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author abhishekuppe
 */
public class ChartServiceTest {

    private static final int STOCK_ID = 1;
    private static final ArrayList<IStock> stockArrayList = new ArrayList<>();
    private static IChartService chartService;

    @BeforeAll
    public static void init() {
        IChartRepository chartRepository = Mockito.mock(ChartRepository.class);

        IStock stock1 = StockAbstractFactory.getInstance().createNewStock();
        stock1.setPrice(new BigDecimal(100));
        stock1.setId(1);
        stock1.setDateTimeCreated(new Date());
        stockArrayList.add(stock1);

        IStock stock2 = StockAbstractFactory.getInstance().createNewStock();
        stock2.setPrice(new BigDecimal(110));
        stock2.setId(2);
        stock2.setDateTimeCreated(new Date());
        stockArrayList.add(stock2);

        Mockito.when(chartRepository.getStockPrices(STOCK_ID)).thenReturn(stockArrayList);

        chartService = new ChartService(chartRepository);
    }

    @Test
    public void getStockPricesDataTest() {
        assertEquals(chartService.getStockPricesData(STOCK_ID), stockArrayList);
    }
}
