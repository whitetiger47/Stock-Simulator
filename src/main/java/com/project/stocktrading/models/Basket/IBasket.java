package com.project.stocktrading.models.Basket;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface IBasket {
    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getChange();

    void setChange(BigDecimal change);

    ArrayList<Integer> getStock_ids();

    void setStock_ids(ArrayList<Integer> stock_ids);

    String getCustom_table_value();

    void setCustom_table_value(String custom_table_value);

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);
}
