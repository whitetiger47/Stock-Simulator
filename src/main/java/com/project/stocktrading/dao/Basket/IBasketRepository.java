package com.project.stocktrading.dao.Basket;

import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.User.IUser;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IBasketRepository {
    int createTable(String name, String createTableQuery);

    int createBasketTable();

    int createBasketDataTable();

    int createBasketWithStocks(IBasket basket, ArrayList<Integer> stockIds, IUser user);

    ArrayList<IBasket> getBaskets(IUser user);

    IBasket getStockIdsForBasket(int basketId);
}
