package com.project.stocktrading.models.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Raj_Valand
 */
public interface IUser {
    BigDecimal getFunds();

    void setFunds(BigDecimal funds);

    Integer getId();

    void setId(Integer id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    String getResidentialId();

    void setResidentialId(String residentialId);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    String getConfirmPassword();

    void setConfirmPassword(String confirmPassword);

    Date getDateOfBirth();

    void setDateOfBirth(Date dateOfBirth);

    String getResetPasswordToken();

    void setResetPasswordToken(String token);

    Integer getIsUserLogin();

    void setIsUserLogin(Integer isUserLogin);

}
