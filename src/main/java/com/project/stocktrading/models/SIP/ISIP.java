package com.project.stocktrading.models.SIP;

import java.math.BigDecimal;

/**
 * @author abhishekuppe
 */
public interface ISIP {
    int getSip_purchased_month();

    void setSip_purchased_month(int sip_purchased_month);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    BigDecimal getChange();

    void setChange(BigDecimal change);

    Integer getMonth_schedule();

    void setMonth_schedule(Integer month_schedule);

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    String getCustom_table_value();

    void setCustom_table_value(String custom_table_value);

    String getBasket_ids();

    void setBasket_ids(String basket_ids);
}
