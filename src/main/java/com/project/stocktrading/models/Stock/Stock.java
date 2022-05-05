package com.project.stocktrading.models.Stock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author abhishekuppe
 */
public class Stock implements IStock {
    private Integer id;
    private String name;
    private String abbreviation;
    private BigDecimal price;
    private Date initialPublicOffering;
    private BigInteger buyCount;
    private BigInteger sellCount;
    private BigDecimal difference;
    private Date dateTimeCreated;

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public BigInteger getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(BigInteger buyCount) {
        this.buyCount = buyCount;
    }

    public BigInteger getSellCount() {
        return sellCount;
    }

    public void setSellCount(BigInteger sellCount) {
        this.sellCount = sellCount;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getInitialPublicOffering() {
        return initialPublicOffering;
    }

    public void setInitialPublicOffering(Date initialPublicOffering) {
        this.initialPublicOffering = initialPublicOffering;
    }
}
