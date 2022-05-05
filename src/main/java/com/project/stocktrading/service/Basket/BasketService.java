package com.project.stocktrading.service.Basket;

import com.project.stocktrading.dao.Basket.IBasketRepository;
import com.project.stocktrading.dao.StockCSV.IStockCSVRepository;
import com.project.stocktrading.dao.StockCSV.StockCSVAbstractFactory;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.Stock.IStock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */

@Service
public class BasketService implements IBasketService {

    private final IBasketRepository basketRepository;
    private final IStockRepository stockRepository;
    private final IUserActions iUserActions;

    public BasketService(IBasketRepository basketRepository, IStockRepository stockRepository, IUserActions iUserActions) {
        this.basketRepository = basketRepository;
        this.stockRepository = stockRepository;
        this.iUserActions = iUserActions;

        this.basketRepository.createBasketTable();
        this.basketRepository.createBasketDataTable();

        IStockCSVRepository stockCSVRepository = StockCSVAbstractFactory.getInstance().createNewStockCSVRepository();
        stockCSVRepository.createStockTable();
        stockCSVRepository.createStockPriceTable();
    }

    public ArrayList<IStock> getAllStocks() {
        return this.stockRepository.getAllStocks();
    }

    public IStock getLatestStockPrice(int id) {
        return this.stockRepository.getLatestStockPrice(id);
    }

    public int createBasketWithStocks(IBasket basket) {
        String customTableValue = basket.getCustom_table_value();
        ArrayList<Integer> stockIds = new ArrayList<>();
        String[] array = customTableValue.split("_");
        for (String ele : array) {
            stockIds.add(Integer.valueOf(ele));
        }
        return this.basketRepository.createBasketWithStocks(basket, stockIds, iUserActions.getUserLoggedIn());
    }

    public ArrayList<IBasket> getBaskets() {
        return this.basketRepository.getBaskets(this.iUserActions.getUserLoggedIn());
    }

    private BigDecimal getBasketPrice(int basketId, boolean isSecondLatest) {
        IBasket basket = this.basketRepository.getStockIdsForBasket(basketId);
        ArrayList<Integer> stockList = basket.getStock_ids();
        BigDecimal totalSum = BigDecimal.valueOf(0);

        for (Integer stockId : stockList) {
            if (isSecondLatest) {
                if (this.stockRepository.getSecondLatestStockPrice(stockId) == null || this.stockRepository.getSecondLatestStockPrice(stockId).getPrice() == null) {
                    totalSum = totalSum.add(new BigDecimal(0));
                } else {
                    totalSum = totalSum.add(this.stockRepository.getSecondLatestStockPrice(stockId).getPrice());
                }
            } else {
                if (this.stockRepository.getLatestStockPrice(stockId) == null || this.stockRepository.getLatestStockPrice(stockId).getPrice() == null) {
                    totalSum = totalSum.add(new BigDecimal(0));
                } else {
                    totalSum = totalSum.add(this.stockRepository.getLatestStockPrice(stockId).getPrice());
                }
            }
        }
        return totalSum;
    }

    public BigDecimal getSecondLatestBasketPrice(int basketId) {
        return this.getBasketPrice(basketId, true);
    }

    public BigDecimal getLatestBasketPrice(int basketId) {
        return this.getBasketPrice(basketId, false);
    }

    public BigDecimal getBasketChange(int basketId) {
        IBasket basket = this.basketRepository.getStockIdsForBasket(basketId);
        ArrayList<Integer> stockList = basket.getStock_ids();
        BigDecimal totalChange = BigDecimal.valueOf(0);

        for (int i = 0; i < stockList.size(); i++) {
            totalChange = (totalChange.add(this.stockRepository.getLatestStockPrice(stockList.get(i)).getPrice()
                    .subtract(this.stockRepository.getSecondLatestStockPrice(stockList.get(i)).getPrice())))
                    .divide(BigDecimal.valueOf(100));
        }
        return totalChange;
    }
}
