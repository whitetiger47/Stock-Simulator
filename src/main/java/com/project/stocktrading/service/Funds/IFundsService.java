package com.project.stocktrading.service.Funds;

import com.project.stocktrading.models.User.User;

import java.math.BigDecimal;

public interface IFundsService {
    BigDecimal getUserFunds();

    void withdrawUserFunds(User user);

    void depositUserFunds(User user);
}
