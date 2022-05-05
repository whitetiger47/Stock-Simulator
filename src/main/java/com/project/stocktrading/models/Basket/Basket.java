package com.project.stocktrading.models.Basket;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public class Basket implements IBasket {

    private Integer id;
    private String name;
    private String custom_table_value;
    private ArrayList<Integer> stock_ids;
    private BigDecimal price;
    private BigDecimal change;

    public Basket() {

    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public ArrayList<Integer> getStock_ids() {
        return stock_ids;
    }

    public void setStock_ids(ArrayList<Integer> stock_ids) {
        this.stock_ids = stock_ids;
    }

    public String getCustom_table_value() {
        return custom_table_value;
    }

    public void setCustom_table_value(String custom_table_value) {
        this.custom_table_value = custom_table_value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
