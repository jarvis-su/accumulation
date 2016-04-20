package com.jarvis.demo.manager.service;

import com.jarvis.supporter.UserMgmtContext;
import com.jarvis.supporter.exception.UserManagerException;
import com.jarvis.users.IActiveUser;
import com.jarvis.users.User;

/**
 * Created by Jarvis on 4/14/16.
 */
public interface IUserManager extends UserMgmtContext {

    public User login(String userName, String password) throws UserManagerException;

    public void validatePassword(User user, String password) throws UserManagerException;

    public boolean isUserEnable(User user) throws UserManagerException;

    public void updateUsers(User user) throws UserManagerException;

    public void changePassword(User user, String newPassword) throws UserManagerException;

    public String encryptPassword(String password) throws UserManagerException;

}
