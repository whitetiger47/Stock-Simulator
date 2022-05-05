package com.project.stocktrading.models.BuySell;

import java.math.BigDecimal;

public interface IBuySell {

    Integer getId();

    void setId(Integer id);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    Integer getIs_buy_sell();

    void setIs_buy_sell(Integer is_buy_sell);

    String getIs_regular_intraday();

    void setIs_regular_intraday(String is_regular_intraday);

    String getIs_limit_market();

    void setIs_limit_market(String is_limit_market);

    Float getStoploss();

    void setStoploss(Float stoploss);

    Float getTarget();

    void setTarget(Float target);

    Integer getQuantity();

    void setQuantity(Integer quantity);

    Integer getStock_id();

    void setStock_id(Integer stock_id);
}
