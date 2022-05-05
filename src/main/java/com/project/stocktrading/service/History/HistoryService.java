package com.project.stocktrading.service.History;

import com.project.stocktrading.dao.History.IHistoryRepository;
import com.project.stocktrading.models.BuySell.IBuySell;
import com.project.stocktrading.models.History.History;
import com.project.stocktrading.models.History.IHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */

@Service
public class HistoryService implements IHistoryService {

    private final IHistoryRepository historyRepository;

    public HistoryService(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public int addToBuySellHistory(IBuySell buySell, int id) {
        IHistory history = new History();
        if (buySell.getIs_buy_sell() == 1)
            history.setValue("buy");
        else
            history.setValue("sell");
        String value = "";
        value = buySell.getQuantity()
                + " " + this.historyRepository.getStockNameByStockId(id) + " Stocks "
                + history.getValue() + " at $" + buySell.getPrice();
        IHistory buySellHistory = new History();
        buySellHistory.setValue(value);
        return this.historyRepository.addToHistory(buySellHistory);
    }

    public int addToFundHistory(IHistory history) {
        String value = "";
        value = history.getPrice() + " $ " + history.getValue();
        if (history.getValue().equals("deposit"))
            value += " to the account";
        else
            value += " from the account";
        IHistory fundHistory = new History();
        fundHistory.setValue(value);
        return this.historyRepository.addToHistory(fundHistory);
    }

    public String getHistoryData() {
        List<Map<String, Object>> historys = this.historyRepository.getHistoryData();
        List<String> historyInfos = transferMapToString(historys);
        String oneHistoryInfo = "";
        for (String historyInfo : historyInfos)
            oneHistoryInfo += historyInfo + "\n";
        return oneHistoryInfo;
    }

    private List<String> transferMapToString(List<Map<String, Object>> historys) {
        List<String> historyInfos = new ArrayList<String>();
        for (Map<String, Object> history : historys) {
            String historyInfo = "";
            historyInfo = history.get("value") + " " + history.get("datetime_created");
            historyInfos.add(historyInfo);
        }
        return historyInfos;
    }
}
