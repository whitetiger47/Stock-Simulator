package com.project.stocktrading.models.Stock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author abhishekuppe
 */
public interface IStock {
    Date getDateTimeCreated();

    void setDateTimeCreated(Date dateTimeCreated);

    BigDecimal getDifference();

    void setDifference(BigDecimal difference);

    BigInteger getBuyCount();

    void setBuyCount(BigInteger buyCount);

    BigInteger getSellCount();

    void setSellCount(BigInteger sellCount);

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    String getAbbreviation();

    void setAbbreviation(String abbreviation);

    BigDecimal getPrice();

    void setPrice(BigDecimal price);

    Date getInitialPublicOffering();

    void setInitialPublicOffering(Date initialPublicOffering);
}
