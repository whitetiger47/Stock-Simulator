package com.project.stocktrading.service.Basket;

import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.Stock.IStock;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IBasketService {

    ArrayList<IStock> getAllStocks();

    IStock getLatestStockPrice(int id);

    int createBasketWithStocks(IBasket basket);

    ArrayList<IBasket> getBaskets();

    BigDecimal getSecondLatestBasketPrice(int basketId);

    BigDecimal getBasketChange(int basketId);

    BigDecimal getLatestBasketPrice(int basketId);
}
