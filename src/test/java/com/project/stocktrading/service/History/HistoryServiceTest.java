package com.project.stocktrading.service.History;

import com.project.stocktrading.dao.History.HistoryRepository;
import com.project.stocktrading.dao.History.IHistoryRepository;
import com.project.stocktrading.models.BuySell.BuySell;
import com.project.stocktrading.models.History.History;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author zhaoling
 */
public class HistoryServiceTest {

    private static IHistoryService historyService;

    @BeforeAll
    public static void init() {
        IHistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
        historyService = new HistoryService(historyRepository);

        String value = "10 Shopify Stocks buy at $11";
        History buySellHistory = new History();
        buySellHistory.setValue(value);

        String value1 = "100 $ deposit to the account";
        History buySellHistory1 = new History();
        buySellHistory1.setValue(value1);

        List<Map<String, Object>> historys = new ArrayList<Map<String, Object>>();
        Map<String, Object> history = new HashMap<String, Object>();
        history.put("value", value);
        history.put("datetime_created", "2021-12-03 22:09:31.225");
        historys.add(history);
        Map<String, Object> history1 = new HashMap<String, Object>();
        history1.put("value", value1);
        history1.put("datetime_created", "2021-12-03 22:09:32.225");
        historys.add(history1);

        System.out.println(new java.sql.Timestamp(new Date().getTime()));
        Mockito.when(historyRepository.getStockNameByStockId(1)).thenReturn("Shopify");
        Mockito.when(historyRepository.getStockNameByStockId(2)).thenReturn("Royal Bank of Canada");
        Mockito.when(historyRepository.addToHistory(buySellHistory)).thenReturn(1);
        Mockito.when(historyRepository.addToHistory(buySellHistory1)).thenReturn(1);
        Mockito.when(historyRepository.getHistoryData()).thenReturn(historys);
    }

    @Test
    public void testAddToBuySellHistory() {
        int expectResult = 0;
        BuySell buysell = new BuySell();
        buysell.setQuantity(10);
        buysell.setPrice(new BigDecimal("11"));
        buysell.setIs_buy_sell(1);
        int id = 1;
        int testResult = historyService.addToBuySellHistory(buysell, id);
        assertEquals(expectResult, testResult);
    }

    @Test
    public void testAddToFundHistory() {
        int expectResult = 0;
        History history = new History();
        history.setPrice(new BigDecimal("100"));
        history.setValue("deposit");
        int testResult = historyService.addToFundHistory(history);
        assertEquals(expectResult, testResult);
    }

    @Test
    public void testGetHistoryData() {
        String expectResult = "10 Shopify Stocks buy at $11 2021-12-03 22:09:31.225"
                + "\n" + "100 $ deposit to the account 2021-12-03 22:09:32.225" + "\n";
        String testResult = historyService.getHistoryData();
        assertEquals(expectResult, testResult);
    }

}
