package com.project.stocktrading.dao.History;

import com.project.stocktrading.models.History.IHistory;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */
public interface IHistoryRepository {

    int createHistoryTable();

    String getStockNameByStockId(Integer stock_id);

    int addToHistory(IHistory history);

    List<Map<String, Object>> getHistoryData();
}
