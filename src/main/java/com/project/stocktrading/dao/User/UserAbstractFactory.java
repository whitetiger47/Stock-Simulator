package com.project.stocktrading.dao.User;

import com.project.stocktrading.models.User.IUser;

public abstract class UserAbstractFactory {
    private static UserAbstractFactory userAbstractFactory = null;

    public static UserAbstractFactory getInstance() {
        if (userAbstractFactory == null) {
            userAbstractFactory = new UserFactory();
        }
        return userAbstractFactory;
    }

    public abstract IUser createNewUser();

    public abstract IUserActions createNewUserRepository();
}
