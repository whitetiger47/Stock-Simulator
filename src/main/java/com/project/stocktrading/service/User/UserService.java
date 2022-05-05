package com.project.stocktrading.service.User;

import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.models.User.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService implements IUserServiceActions {

    private final IUserActions iUserActions;

    protected UserService(IUserActions iUserActions) {
        this.iUserActions = iUserActions;

    }

    public boolean userRegistration(User user) {
        String encryptedPassword = encryptedPassword(user.getPassword());
        BigDecimal funds = new BigDecimal("0");
        if (user.getFunds() == null) {
            funds = new BigDecimal("10000.00");
        } else {
            funds = user.getFunds();
        }
        try {
            this.iUserActions.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), encryptedPassword,
                    user.getResidentialId(), user.getDateOfBirth(), funds);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String encryptedPassword(String originalPassword) {
        String encryptedPassword = "";
        for (int i = 0; i < originalPassword.length(); i++) {
            char c = originalPassword.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char) (c + 2);
                if (c > 'Z')
                    c = (char) (c - 'Z' + 'A' - 1);
            } else if (c >= 'a' && c <= 'z') {
                c = (char) (c + 2);
                if (c > 'z')
                    c = (char) (c - 'z' + 'a' - 1);
            } else if (c >= '0' && c <= '9') {
                c = (char) (c + 2);
                if (c > '9')
                    c = (char) (c - '9' + '0' - 1);
            }
            encryptedPassword = encryptedPassword + c;
        }
        return encryptedPassword;
    }

    public String decryptedPassword(String passwordFromDb) {
        String decrypedPassword = "";
        for (int i = 0; i < passwordFromDb.length(); i++) {
            char c = passwordFromDb.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char) (c - 2);
                if (c > 'Z')
                    c = (char) (c + 'Z' - 'A' + 1);
            } else if (c >= 'a' && c <= 'z') {
                c = (char) (c - 2);
                if (c > 'z')
                    c = (char) (c + 'z' - 'a' + 1);
            } else if (c >= '0' && c <= '9') {
                c = (char) (c - 2);
                if (c > '9')
                    c = (char) (c + '9' - '0' + 1);
            }
            decrypedPassword = decrypedPassword + c;
        }
        return decrypedPassword;
    }

    public IUser getUserInfoFromEmail(String name) {
        IUser user = this.iUserActions.getUserFromDatabase(name);
        return user;
    }

    public int updateUserFunds(String email, BigDecimal funds) {
        IUser user = this.iUserActions.getUserFromDatabase(email);
        user.setFunds(funds);
        return this.iUserActions.updateFunds(user);
    }

    public int updateUserLogedIn(String email) {
        return this.iUserActions.setUserLoggedIn(email);
    }

    public boolean userLoggedOut() {
        return this.iUserActions.userLogout();
    }

    public IUser getCurrentUser() {
        return this.iUserActions.getUserLoggedIn();
    }


}
