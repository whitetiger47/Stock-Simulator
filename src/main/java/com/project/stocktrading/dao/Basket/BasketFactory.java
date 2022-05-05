package com.project.stocktrading.dao.Basket;

import com.project.stocktrading.models.Basket.Basket;
import com.project.stocktrading.models.Basket.IBasket;

/**
 * @author abhishekuppe
 */
public class BasketFactory extends BasketAbstractFactory {

    @Override
    public IBasket createNewBasket() {
        return new Basket();
    }

    @Override
    public IBasketRepository createNewBasketRepository() {
        return new BasketRepository();
    }
}
