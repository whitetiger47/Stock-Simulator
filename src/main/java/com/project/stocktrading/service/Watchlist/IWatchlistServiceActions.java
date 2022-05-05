package com.project.stocktrading.service.Watchlist;

import com.project.stocktrading.models.BuySell.BuySell;
import com.project.stocktrading.models.BuySell.IBuySell;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.User.IUser;

import java.math.BigDecimal;

public interface IWatchlistServiceActions {

    int addWatchlistValue(int watchlist_id, int stock_id);

    void updateFunds(IBuySell buySell, IUser user, int id);

    void addBuyStock(BuySell buySell, int id, BigDecimal price, IUser user, IStock s);

    boolean updateBuySellCount(int id, int quantity, IBuySell buySell, IUser user);

}
