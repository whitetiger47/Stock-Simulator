package com.project.stocktrading.dao.Watchlist;

import com.project.stocktrading.models.BuySell.IBuySell;

public abstract class WatchlistAbstractFactory {
    private static WatchlistAbstractFactory watchlistAbstractFactory = null;

    public static WatchlistAbstractFactory getInstance() {
        if (watchlistAbstractFactory == null) {
            watchlistAbstractFactory = new WatchlistFactory();
        }
        return watchlistAbstractFactory;
    }

    public abstract IWatchListActions createNewWatchlistRepository();

    public abstract IBuySell createNewBuySell();
}

