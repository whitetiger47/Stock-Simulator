package com.project.stocktrading.dao.User;

import com.project.stocktrading.models.User.IUser;

import java.sql.ResultSet;

public abstract class NewUser {
    protected IUser getNewUser(ResultSet resultSet) {
        IUser iUser = UserAbstractFactory.getInstance().createNewUser();
        try {
            iUser.setId(resultSet.getInt("id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iUser.setFirstName(resultSet.getString("firstName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iUser.setLastName(resultSet.getString("lastName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iUser.setEmail(resultSet.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iUser.setResidentialId(resultSet.getString("residentialId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iUser.setDateOfBirth(resultSet.getDate("dateOfBirth"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            iUser.setFunds(resultSet.getBigDecimal("funds"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iUser;
    }
}




