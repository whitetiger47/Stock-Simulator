package com.project.stocktrading.dao.User;

import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.models.User.User;

public class UserFactory extends UserAbstractFactory {

    @Override
    public IUser createNewUser() {
        return new User();
    }

    @Override
    public IUserActions createNewUserRepository() {
        return new UserRepository();
    }
}
