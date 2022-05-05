package com.project.stocktrading.service.Watchlist;

import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.Watchlist.IWatchListActions;
import com.project.stocktrading.models.BuySell.BuySell;
import com.project.stocktrading.models.BuySell.IBuySell;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.service.History.IHistoryService;
import com.project.stocktrading.service.Stocks.Stocks.IStockService;
import com.project.stocktrading.service.User.IUserServiceActions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Raj_Valand
 */

@Service
public class WatchListService implements IWatchlistServiceActions {

    private final IWatchListActions IWatchListActions;
    private final IWatchlistGetServiceActions iWatchlistGetServiceActions;
    private final int STOCK_PRICE_COUNTING_DURATION = 10000 * 100;
    private final int INITIAL_DELAY = 1000;

    private final IStockService iStockService;
    private final IHistoryService historyService;
    private final IUserServiceActions iUserServiceActions;
    private final IStockRepository stockRepository;


    public WatchListService(IWatchListActions IWatchListActions, IWatchlistGetServiceActions iWatchlistGetServiceActions, IStockService iStockService, IUserServiceActions iUserServiceActions, IHistoryService historyService) {
        this.iWatchlistGetServiceActions = iWatchlistGetServiceActions;
        this.stockRepository = StockAbstractFactory.getInstance().createNewStockRepository();

        this.IWatchListActions = IWatchListActions;
        this.iStockService = iStockService;
        this.iUserServiceActions = iUserServiceActions;
        this.IWatchListActions.createWatchLists();
        this.historyService = historyService;
    }

    public int addWatchlistValue(int watchlist_id, int stock_id) {
        return this.IWatchListActions.addWatchList(watchlist_id, stock_id);
    }


    public void updateFunds(IBuySell buySell, IUser user, int id) {
        //Calculate total price by multiplying price with quantity
        BigDecimal total_price = BigDecimal.valueOf(buySell.getQuantity()).multiply(buySell.getPrice());

        //Check whether it is buy or sell
        if (buySell.getIs_buy_sell() == 1) {
            try {
                // check user has enough funds or not
                // for buy stock update amount from fund
                BigDecimal diff = user.getFunds().subtract(total_price);
                if ((user.getFunds()).compareTo(total_price) > 0) {
                    user.setFunds(diff);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // No condition for sell just update funds to user account
            BigDecimal addition = user.getFunds().add(total_price);
            user.setFunds(addition);
        }

        //redirect to updateBuySellCount to update stock count
        boolean chk = updateBuySellCount(id, buySell.getQuantity(), buySell, user);
        if (chk) {
            //if above step is successfully run then perform operation on funds
            iUserServiceActions.updateUserFunds(user.getEmail(), user.getFunds());
        }
    }

    // Differentiate Market and Limit stock category
    public void addBuyStock(BuySell buySell, int id, BigDecimal price, IUser user, IStock s) {

        buySell.setPrice(price);

        //Check for Market stock category
        if (buySell.getIs_limit_market().equals("Market")) {
            //Set stoploss and target null when stock category is market
            buySell.setStoploss(null);
            buySell.setTarget(null);

            //redirect to update user funds
            updateFunds(buySell, user, id);
        }

        //save to history
        this.historyService.addToBuySellHistory(buySell, id);

        // update BuySell table and redirect to another function
        this.IWatchListActions.addBuyStockIntoBuySell(id, buySell.getIs_buy_sell(), buySell.getIs_regular_intraday(), buySell.getIs_limit_market(), buySell.getStoploss(), buySell.getTarget(),
                buySell.getQuantity(), price);

        //Start timer amd check in database when encountered stock has Limit category in BuySell table
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //Collect list of BuySell row has Limit category
                List<IBuySell> b = IWatchListActions.getLimitStock();
                for (int i = 0; i < b.size(); i++) {
                    // just for development purpose
                    System.out.println("Iterating for Limit");
                    //get the exact price when stock encountered
                    BigDecimal price = b.get(i).getPrice();

                    //getting latest price at current time

                    IStock s = iStockService.getLatestStockPrice(b.get(i).getStock_id());


                    //get stoploss and target from encountered row
                    BigDecimal stoploss = BigDecimal.valueOf(b.get(i).getStoploss());
                    BigDecimal target = BigDecimal.valueOf(b.get(i).getTarget());

                    //business logic for stoploss and target
                    stoploss = price.subtract(stoploss.divide(BigDecimal.valueOf(100)));
                    target = price.add(target.divide(BigDecimal.valueOf(100)));

                    //Check whether current price is less than stoploss or greater than target
                    if (s.getPrice().compareTo(stoploss) <= 0 || s.getPrice().compareTo(target) >= 0) {
                        //redirect to updateLimitBuySell for updating the table and change limit to market
                        IWatchListActions.updateLimitBuySell(b.get(i).getId());

                        //Logic to update funds
                        updateFunds(b.get(i), user, b.get(i).getStock_id());
                    }

                }

            }
        }, INITIAL_DELAY, STOCK_PRICE_COUNTING_DURATION);
    }

    public boolean updateBuySellCount(int id, int quantity, IBuySell buySell, IUser user) {

        // get stock from id
        IStock stock = iWatchlistGetServiceActions.getStockById(id);
        IStock s = this.iStockService.getLatestStockPrice(stock.getId());
        stock.setPrice(s.getPrice());

        // total price calculated
        BigDecimal total_price = stock.getPrice().multiply(BigDecimal.valueOf(quantity));

        //business logic for buy
        if (buySell.getIs_buy_sell() == 1) {
            if (user.getFunds().compareTo(total_price) < 0) {
                //if user has not enough funds then return false;
                return false;
            }
            // user has enough fund then update buy count
            // increase buycount by adding quantity to it
            stock.setBuyCount(stock.getBuyCount().add(BigInteger.valueOf(quantity)));

        }
        // business logic for sell stock
        else {
            try {
                // check whether enough stock bought
                // example: buy stock - 20 then we can't sell more than 20
                if (stock.getBuyCount().compareTo(BigInteger.valueOf(quantity)) >= 0) {
                    stock.setBuyCount(stock.getBuyCount().subtract(BigInteger.valueOf(quantity)));
                    stock.setSellCount(stock.getSellCount().add(BigInteger.valueOf(quantity)));
                } else
                    return false;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // both condition is true then redirect to update stock buy count
        this.IWatchListActions.updateStockBuyCount(stock);
        return true;
    }


}
