package com.project.stocktrading.models.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Raj_Valand
 */


public class User implements IUser {

    public BigDecimal withdrawUserFunds;
    private Integer id;
    private String firstName;
    private String lastName;
    private String residentialId;
    private String email;
    private String password;
    private String confirmPassword;
    private Date dateOfBirth;
    private BigDecimal Funds;
    private Integer isUserLogin;
    private String token;

    public Integer getIsUserLogin() {
        return isUserLogin;
    }

    public void setIsUserLogin(Integer isUserLogin) {
        this.isUserLogin = isUserLogin;
    }

    public BigDecimal getFunds() {
        return Funds;
    }

    public void setFunds(BigDecimal funds) {
        Funds = funds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResidentialId() {
        return residentialId;
    }

    public void setResidentialId(String residentialId) {
        this.residentialId = residentialId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResetPasswordToken() {
        return token;
    }

    public void setResetPasswordToken(String token) {
        this.token = token;
    }

    public BigDecimal getWithdrawUserFunds() {
        return withdrawUserFunds;
    }

    public void setWithdrawUserFunds(BigDecimal withdrawUserFunds) {
        this.withdrawUserFunds = withdrawUserFunds;
    }
}
