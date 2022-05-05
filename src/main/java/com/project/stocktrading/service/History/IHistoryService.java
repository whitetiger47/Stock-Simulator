package com.project.stocktrading.service.History;

import com.project.stocktrading.models.BuySell.IBuySell;
import com.project.stocktrading.models.History.IHistory;

/**
 * @author zhaoling
 */
public interface IHistoryService {

    int addToBuySellHistory(IBuySell buySell, int id);

    int addToFundHistory(IHistory history);

    String getHistoryData();

}
