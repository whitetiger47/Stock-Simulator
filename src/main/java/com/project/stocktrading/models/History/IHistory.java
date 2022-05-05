package com.project.stocktrading.models.History;

import java.math.BigDecimal;

/**
 * @author zhaoling
 */
public interface IHistory {

    String getValue();

    void setValue(String value);

    Integer getStock_id();

    void setStock_id(Integer stock_id);

    Integer getQuantity();

    void setQuantity(Integer quantity);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);
}
