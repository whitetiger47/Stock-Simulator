package com.project.stocktrading.service.User;

public interface IUserAuthServiceActions {

    boolean userIsAlreadyExists(String email);

    boolean residentialIdAlreadyExists(String residentialId);

    boolean isUserCredentialValid(String email, String password);

    boolean isAdminCredentialValid(String email, String password);

    boolean isPasswordNotValid(String password);

}
