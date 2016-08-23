package com.jarvis.demo.manager.service;

import com.jarvis.supporter.UserMgmtContext;
import com.jarvis.supporter.exception.UserManagerException;
import com.jarvis.users.IActiveUser;
import com.jarvis.users.User;

/**
 * Created by Jarvis on 4/14/16.
 */
public interface IUserManager extends UserMgmtContext {

    User login(String userName, String password) throws UserManagerException;

    void validatePassword(User user, String password) throws UserManagerException;

    boolean isUserEnable(User user) throws UserManagerException;

    void updateUsers(User user) throws UserManagerException;

    void changePassword(User user, String newPassword) throws UserManagerException;

    String encryptPassword(String password) throws UserManagerException;

}
