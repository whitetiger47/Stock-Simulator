package com.project.stocktrading.dao.User;

import com.project.stocktrading.models.User.IUser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Raj_Valand
 */

public interface IUserActions {

    int registerUser(String firstName, String lastName, String email, String password, String residentialId, Date dateOfBirth, BigDecimal funds) throws Exception;

    boolean checkUserAlreadyExists(String username);

    int updateFunds(IUser user);

    boolean checkResidentialIdAlreadyExists(String residentialId);

    String getUserPasswordFromDb(String email);

    String getAdminPasswordFromDb(String email);

    IUser getUserFromDatabase(String username);

    BigDecimal getFunds();

    IUser getUserLoggedIn();

    int setUserLoggedIn(String username);

    boolean userLogout();


}
