package com.project.stocktrading.service.Watchlist;

import com.project.stocktrading.models.Stock.IStock;

import java.util.List;

public interface IWatchlistGetServiceActions {

    List<Integer> getStockFromWatchListValue(int watchlist_id);

    List<IStock> getStockFromID(int watchlist_id);

    IStock getStockById(int id);
}
