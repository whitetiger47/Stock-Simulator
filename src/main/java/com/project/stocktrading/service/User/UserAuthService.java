package com.project.stocktrading.service.User;

import com.project.stocktrading.dao.User.IUserActions;
import org.springframework.stereotype.Service;

/**
 * @author Raj_Valand
 */

@Service
public class UserAuthService implements IUserAuthServiceActions {

    private final IUserActions iUserActions;
    private final IUserServiceActions iUserServiceActions;

    public UserAuthService(IUserActions iUserActions, IUserServiceActions iUserServiceActions) {
        this.iUserActions = iUserActions;
        this.iUserServiceActions = iUserServiceActions;
    }

    public boolean userIsAlreadyExists(String email) {
        boolean userAlreadyExists = false;
        userAlreadyExists = this.iUserActions.checkUserAlreadyExists(email);
        return userAlreadyExists;
    }

    public boolean residentialIdAlreadyExists(String residentialId) {
        boolean residentialIdExists = false;
        residentialIdExists = this.iUserActions.checkResidentialIdAlreadyExists(residentialId);
        return residentialIdExists;
    }

    public boolean isUserCredentialValid(String email, String password) {
        boolean validCredentials = false;
        String userPassword = this.iUserActions.getUserPasswordFromDb(email);
        if (userPassword != null && !userPassword.isEmpty()) {
            if (this.iUserServiceActions.decryptedPassword(userPassword).equals(password))
                validCredentials = true;
        }
        return validCredentials;
    }

    public boolean isAdminCredentialValid(String email, String password) {
        boolean validCredentials = false;
        String adminPassword = this.iUserActions.getAdminPasswordFromDb(email);
        if (adminPassword != null && !adminPassword.isEmpty()) {
            if (adminPassword.equals(password)) {
                validCredentials = true;
            }
        }
        return validCredentials;
    }

    public boolean isPasswordNotValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        return !password.matches(pattern);
    }
}
