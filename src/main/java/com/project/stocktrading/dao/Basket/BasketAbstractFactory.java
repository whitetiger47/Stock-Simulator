package com.project.stocktrading.dao.Basket;

import com.project.stocktrading.models.Basket.IBasket;

/**
 * @author abhishekuppe
 */
public abstract class BasketAbstractFactory {
    private static BasketAbstractFactory basketAbstractFactory = null;

    public static BasketAbstractFactory getInstance() {
        if (basketAbstractFactory == null) {
            basketAbstractFactory = new BasketFactory();
        }
        return basketAbstractFactory;
    }

    public abstract IBasket createNewBasket();

    public abstract IBasketRepository createNewBasketRepository();
}
