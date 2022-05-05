package com.project.stocktrading.models.SIP;

import java.math.BigDecimal;

/**
 * @author abhishekuppe
 */
public class SIP implements ISIP {
    private Integer id;
    private String name;
    private String custom_table_value;
    private String basket_ids;
    private Integer month_schedule;
    private BigDecimal price;
    private BigDecimal change;
    private int sip_purchased_month;

    public int getSip_purchased_month() {
        return sip_purchased_month;
    }

    public void setSip_purchased_month(int sip_purchased_month) {
        this.sip_purchased_month = sip_purchased_month;
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

    public Integer getMonth_schedule() {
        return month_schedule;
    }

    public void setMonth_schedule(Integer month_schedule) {
        this.month_schedule = month_schedule;
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

    public String getCustom_table_value() {
        return custom_table_value;
    }

    public void setCustom_table_value(String custom_table_value) {
        this.custom_table_value = custom_table_value;
    }

    public String getBasket_ids() {
        return basket_ids;
    }

    public void setBasket_ids(String basket_ids) {
        this.basket_ids = basket_ids;
    }
}
