package com.project.stocktrading.service.User;

import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.models.User.User;

import java.math.BigDecimal;

public interface IUserServiceActions {

    boolean userRegistration(User user);

    IUser getUserInfoFromEmail(String name);

    int updateUserFunds(String email, BigDecimal funds);

    int updateUserLogedIn(String email);

    IUser getCurrentUser();

    String encryptedPassword(String originalPassword);

    String decryptedPassword(String passwordFromDb);

    boolean userLoggedOut();

}
