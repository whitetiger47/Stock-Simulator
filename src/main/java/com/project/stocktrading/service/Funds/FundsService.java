package com.project.stocktrading.service.Funds;
/**
 * @author Smit_Thakkar
 */


import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.User.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FundsService implements IFundsService {

    private final UserRepository userRepository;

    public FundsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BigDecimal getUserFunds() {

        return this.userRepository.getFunds();

    }

    public void withdrawUserFunds(User user) {
        this.userRepository.withdrawUserFunds(user);
    }

    public void depositUserFunds(User user) {
        this.userRepository.depositUserFunds(user);
    }
}
