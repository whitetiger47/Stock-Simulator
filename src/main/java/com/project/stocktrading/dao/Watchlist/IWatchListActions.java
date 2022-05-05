package com.project.stocktrading.dao.Watchlist;

import com.project.stocktrading.models.BuySell.IBuySell;
import com.project.stocktrading.models.Stock.IStock;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Raj_Valand
 */

public interface IWatchListActions {

    int createWathchlistTable();

    int createBuySellTable();

    int addWatchList(int stock_id, int watchlist_id);

    List<Integer> getStockIdfromWatchId(int watchlist_id);

    int addBuyStockIntoBuySell(Integer stock_id, Integer isBuy_Sell, String is_regular_intraday, String is_limit_market, Float stoploss,
                               Float target, Integer quantity, BigDecimal price);

    int updateLimitBuySell(int id);

    List<IBuySell> getLimitStock();

    void createWatchLists();

    int updateStockBuyCount(IStock stock);
}
