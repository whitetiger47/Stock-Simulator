package com.project.stocktrading.dao.Watchlist;

import com.project.stocktrading.models.BuySell.BuySell;
import com.project.stocktrading.models.BuySell.IBuySell;

public class WatchlistFactory extends WatchlistAbstractFactory {
    @Override
    public IBuySell createNewBuySell() {
        return new BuySell();
    }

    @Override
    public IWatchListActions createNewWatchlistRepository() {
        return new WatchListRepository();
    }
}
