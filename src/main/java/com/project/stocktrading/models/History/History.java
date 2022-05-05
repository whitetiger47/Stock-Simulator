package com.project.stocktrading.models.History;

import java.math.BigDecimal;

/**
 * @author zhaoling
 */
public class History implements IHistory {

    private String value;
    private Integer stock_id;
    private Integer quantity;
    private BigDecimal price;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStock_id() {
        return stock_id;
    }

    public void setStock_id(Integer stock_id) {
        this.stock_id = stock_id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
