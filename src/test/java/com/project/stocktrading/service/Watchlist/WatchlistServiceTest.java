package com.project.stocktrading.service.Watchlist;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Watchlist.IWatchListActions;
import com.project.stocktrading.dao.Watchlist.WatchListRepository;
import com.project.stocktrading.service.History.IHistoryService;
import com.project.stocktrading.service.Stocks.Stocks.IStockService;
import com.project.stocktrading.service.User.IUserServiceActions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WatchlistServiceTest {
    private static final Integer watchlistId = 1;
    private static final Integer stocksId = 1;
    private static final ArrayList<Integer> stockId = new ArrayList<>();
    private static IWatchlistServiceActions iWatchlistServiceActions;
    private static IWatchlistGetServiceActions iWatchlistGetServiceActions;
    private static IStockService iStockService;
    private static IHistoryService iHistoryService;
    private static IStockRepository iStockRepository;
    private static IUserServiceActions iUserServiceActions;


    @BeforeAll
    public static void init() throws Exception {
        IWatchListActions iWatchListActions = Mockito.mock(WatchListRepository.class);
        Mockito.when(iWatchListActions.addWatchList(watchlistId, stocksId)).thenReturn(1);
        stockId.add(1);
        stockId.add(2);
        Mockito.when(iWatchListActions.getStockIdfromWatchId(watchlistId)).thenReturn(stockId);
        iWatchlistServiceActions = new WatchListService(iWatchListActions, iWatchlistGetServiceActions, iStockService, iUserServiceActions, iHistoryService);
        iWatchlistGetServiceActions = new WatchlistGetService(iWatchListActions, iStockRepository);

    }

    @Test
    public void getStockIdFromWatchlistIdTest() {
        assertEquals(1, iWatchlistServiceActions.addWatchlistValue(watchlistId, stocksId));
    }

    @Test
    public void getStockFromWatchlistValueTest() {
        assertEquals(stockId, iWatchlistGetServiceActions.getStockFromWatchListValue(watchlistId));
    }
}
