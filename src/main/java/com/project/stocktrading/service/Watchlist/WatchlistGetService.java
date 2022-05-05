package com.project.stocktrading.service.Watchlist;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Watchlist.IWatchListActions;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchlistGetService implements IWatchlistGetServiceActions {

    private final IWatchListActions IWatchListActions;
    private final IStockRepository iStockRepository;

    public WatchlistGetService(IWatchListActions iWatchListActions, IStockRepository iStockRepository) {
        IWatchListActions = iWatchListActions;
        this.iStockRepository = iStockRepository;
    }

    public List<Integer> getStockFromWatchListValue(int watchlist_id) {
        return this.IWatchListActions.getStockIdfromWatchId(watchlist_id);
    }

    public List<IStock> getStockFromID(int watchlist_id) {
        List<Integer> stocks = getStockFromWatchListValue(watchlist_id);
        List<IStock> stock = new ArrayList<>();
        for (int i = 0; i < stocks.size(); i++) {
            stock.add(this.iStockRepository.getStock(stocks.get(i)));
        }
        return stock;
    }

    public IStock getStockById(int id) {
        return this.iStockRepository.getStock(id);
    }

}
