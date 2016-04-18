package com.jarvis.demo.manager.service;

import com.jarvis.demo.manager.dao.UserManagerDao;
import com.jarvis.users.IActiveUser;
import com.jarvis.users.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Jarvis on 4/14/16.
 */
public class UserManagerImpl implements IUserManager {

    @Autowired
    public UserManagerDao userDao;

    @Override
    public User login(String userName, String password) {
        User activeUser  = userDao.getUserInfo(userName);


        return activeUser;
    }

    @SuppressWarnings("unused")
	private void validatePassword(IActiveUser activeUser, String password){

    }

    @Override
    public boolean isUserEnable(IActiveUser user) {
        return false;
    }

    @Override
    public void updateUsers(IActiveUser user) {

    }
}
