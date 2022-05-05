package com.project.stocktrading.service.Funds;


import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.User.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Smit_Thakkar
 */
public class FundsServiceTest {


    @BeforeAll
    public static void init() {
        IUserActions userActions = Mockito.mock(UserRepository.class);


        User user = new User();
        user.setId(1);
        user.setFunds(null);

        Mockito.when(userActions.getFunds()).thenReturn(null);


    }

    @Test
    public void testUserFunds() {
        User user = new User();
        BigDecimal bigDecimal = user.getFunds();
        assertEquals(bigDecimal, null);

    }

    @Test
    public void testWithdrawFunds() {
        User user = new User();
        BigDecimal bigDecimal = user.getWithdrawUserFunds();
        BigDecimal bigDecimal1 = user.getFunds();
        assertEquals(bigDecimal1, bigDecimal);
    }
}
