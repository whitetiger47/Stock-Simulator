package com.project.stocktrading.controller.Home;


import com.project.stocktrading.models.BuySell.BuySell;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.Stock.Stock;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.service.History.HistoryService;
import com.project.stocktrading.service.Stocks.Stocks.IStockService;
import com.project.stocktrading.service.User.IUserServiceActions;
import com.project.stocktrading.service.Watchlist.IWatchlistGetServiceActions;
import com.project.stocktrading.service.Watchlist.IWatchlistServiceActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raj_Valand
 */


@Controller
public class WatchListController {

    private final IStockService iStockService;
    private final IWatchlistServiceActions iWatchlistServiceActions;
    private final IUserServiceActions iUserServiceActions;
    private final HistoryService historyService;
    private final IWatchlistGetServiceActions iWatchlistGetServiceActions;

    @Autowired
    public WatchListController(
            IStockService iStockService, IWatchlistServiceActions iWatchlistServiceActions,
            IUserServiceActions iUserServiceActions,
            HistoryService historyService, IWatchlistGetServiceActions iWatchlistGetServiceActions) {
        this.iStockService = iStockService;
        this.iWatchlistServiceActions = iWatchlistServiceActions;
        this.iUserServiceActions = iUserServiceActions;
        this.historyService = historyService;
        this.iWatchlistGetServiceActions = iWatchlistGetServiceActions;
    }

    @GetMapping("/watchlisthome")
    public String getWatchlistHome() {
        return "redirect:/watchlisthome/1";
    }


    @GetMapping("/watchlisthome/{id}")
    public String getSpecificWatchlist(Model model, @PathVariable(name = "id") int id) {
        ArrayList<IStock> stocks = this.iStockService.getStock();
        model.addAttribute("stocks", stocks);
        List<IStock> s = this.iWatchlistGetServiceActions.getStockFromID(id);
        List<Integer> stock_id = this.iWatchlistGetServiceActions.getStockFromWatchListValue(id);
        for (int i = 0; i < stock_id.size(); i++) {
            IStock s2 = this.iStockService.getLatestStockPrice(stock_id.get(i));
            s.get(i).setPrice(s2.getPrice());
            IStock s3 = this.iStockService.getDifferenceOfStockValue(s.get(i).getId());
            s.get(i).setDifference(s3.getDifference());
        }
        model.addAttribute("s", s);
        String historyString = this.historyService.getHistoryData();
        model.addAttribute("historyString", historyString);
        return "watchlist/watchlisthome";
    }

    @PostMapping("/watchlisthome/{id}")
    public String postHme(@ModelAttribute Stock stock, @PathVariable(name = "id") int id) {
        int stock_id = stock.getId();
        int watchlist_id = id;
        this.iWatchlistServiceActions.addWatchlistValue(watchlist_id, stock_id);
        return "watchlist/watchlisthome";
    }

    @GetMapping("/watchlisthome/buy/{id}")
    public String getBuy(Model model, @PathVariable(name = "id") int id, @ModelAttribute Stock stock) {
        model.addAttribute("buy", new BuySell());
        model.addAttribute("stock", stock);
        return "Buy_Sell/Buy";
    }

    // Post method for passed from Buy form
    @PostMapping("/watchlisthome/buy/{id}")
    public String postBuy(Model model, @ModelAttribute BuySell buySell, @PathVariable(name = "id") int id) {
        // 1 --> stands for Buy
        buySell.setIs_buy_sell(1);

        // get stock bought by user
        IStock s = iWatchlistGetServiceActions.getStockById(id);

        // get latest price at time user bought stock
        IStock stock1 = iStockService.getLatestStockPrice(id);

        // set price to the stock
        s.setPrice(stock1.getPrice());
        BigDecimal price = stock1.getPrice();
        IUser user = this.iUserServiceActions.getCurrentUser();

        // perform operation for addBuyStock redirect to Watchlist service
        this.iWatchlistServiceActions.addBuyStock(buySell, id, price, user, s);
        return "watchlist/watchlisthome";
    }

    @GetMapping("/watchlisthome/sell/{id}")
    public String getSell(Model model, @PathVariable(name = "id") int id, @ModelAttribute Stock stock) {
        model.addAttribute("sell", new BuySell());
        model.addAttribute("stock", stock);
        return "Buy_Sell/Sell";
    }

    @PostMapping("/watchlisthome/sell/{id}")
    public String postSell(Model model, @ModelAttribute BuySell buySell, Stock stock, @PathVariable(name = "id") int id) {

        // 0 --> stands for Sell
        buySell.setIs_buy_sell(0);

        // get stock bought by user
        IStock s = iWatchlistGetServiceActions.getStockById(id);

        // get latest price at time user bought stock
        IStock stock1 = iStockService.getLatestStockPrice(id);

        // set price to the stock
        s.setPrice(stock1.getPrice());
        BigDecimal price = stock1.getPrice();

        IUser user = this.iUserServiceActions.getCurrentUser();
        // perform operation for addBuyStock redirect to Watchlist service
        this.iWatchlistServiceActions.addBuyStock(buySell, id, price, user, s);
        return "watchlist/watchlisthome";
    }

}
