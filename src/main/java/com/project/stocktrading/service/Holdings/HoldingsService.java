package com.project.stocktrading.service.Holdings;

import com.project.stocktrading.dao.Holdings.IHoldingsRepository;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */

@Service
public class HoldingsService implements IHoldingsService {
    private final IHoldingsRepository holdingsRepository;
    private final IStockRepository stockRepository;

    public HoldingsService(IHoldingsRepository holdingsRepository, IStockRepository stockRepository) {
        this.holdingsRepository = holdingsRepository;
        this.stockRepository = stockRepository;
    }

    public List<Map<String, Object>> getAllHoldingsFromDb() {
        List<Map<String, Object>> allHoldings = this.holdingsRepository.getAllHoldingsFromDb();
        for (Map<String, Object> holding : allHoldings) {
            holding.put("price", this.stockRepository.getLatestStockPrice((Integer) holding.get("stock_id")).getPrice());
        }
        return allHoldings;
    }
}
