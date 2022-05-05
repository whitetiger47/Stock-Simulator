package com.project.stocktrading;

import com.project.stocktrading.service.SIP.SIPBackground.ISIPBackgroundService;
import com.project.stocktrading.service.Stocks.Stocks.IStockService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author abhishekuppe
 */

@SpringBootApplication
public class StockTradingApplication {


    private final IStockService stockService;
    private final ISIPBackgroundService sipBackgroundService;

    public StockTradingApplication(IStockService stockService, ISIPBackgroundService sipBackgroundService) {

        this.stockService = stockService;
        this.sipBackgroundService = sipBackgroundService;

        this.stockService.runInBackground();
        this.sipBackgroundService.runSIPInBackground();
    }

    public static void main(String[] args) {
        SpringApplication.run(StockTradingApplication.class, args);
    }
}
