package com.jarvis.demo.manager.service;

import com.jarvis.users.IActiveUser;

/**
 * Created by Jarvis on 4/14/16.
 */
public class UserManagerImpl implements IUserManager {
    @Override
    public IActiveUser login(String userName, String password) {
        return null;
    }

    @Override
    public boolean isUserEnable(IActiveUser user) {
        return false;
    }

    @Override
    public void updateUsers(IActiveUser user) {

    }
}
