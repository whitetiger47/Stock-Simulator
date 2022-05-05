package com.project.stocktrading.models.BuySell;

import java.math.BigDecimal;

public class BuySell implements IBuySell {

    private Integer id;
    private Integer stock_id;
    private Integer is_buy_sell;
    private String is_regular_intraday;
    private String is_limit_market;
    private Float stoploss;
    private Float target;
    private Integer quantity;
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIs_buy_sell() {
        return is_buy_sell;
    }

    public void setIs_buy_sell(Integer is_buy_sell) {
        this.is_buy_sell = is_buy_sell;
    }

    public String getIs_regular_intraday() {
        return is_regular_intraday;
    }

    public void setIs_regular_intraday(String is_regular_intraday) {
        this.is_regular_intraday = is_regular_intraday;
    }

    public String getIs_limit_market() {
        return is_limit_market;
    }

    public void setIs_limit_market(String is_limit_market) {
        this.is_limit_market = is_limit_market;
    }

    public Float getStoploss() {
        return stoploss;
    }

    public void setStoploss(Float stoploss) {
        this.stoploss = stoploss;
    }

    public Float getTarget() {
        return target;
    }

    public void setTarget(Float target) {
        this.target = target;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStock_id() {
        return stock_id;
    }

    public void setStock_id(Integer stock_id) {
        this.stock_id = stock_id;
    }

}
