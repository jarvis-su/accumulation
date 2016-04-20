package com.jarvis.demo.manager.service;

import com.jarvis.demo.manager.dao.UserManagerDao;

import com.jarvis.supporter.exception.UserManagerException;
import com.jarvis.supporter.logger.Log4jAdapter;
import jarvis.utils.DesUtil;
import com.jarvis.users.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Jarvis on 4/14/16.
 */
public class UserManagerImpl implements IUserManager {

    protected Log4jAdapter logger = Log4jAdapter.getLog4jAdapter(this.getClass().getName());

    @Autowired
    public UserManagerDao userDao;

    @Override
    public User login(String userName, String password) throws UserManagerException {
        User user = userDao.getUserInfo(userName);
        validatePassword(user, password);
        isUserEnable(user);
        return user;
    }

    @Override
    public void validatePassword(User user, String password) throws UserManagerException {

        String passwordEncrypt = encryptPassword(password);
        if (passwordEncrypt.equals(user.getPassword())) {
            logger.debug("User password is matched, got the correct login request !");
        } else {
            throw new UserManagerException(1, "Incorrect password ");
        }

    }

    @Override
    public boolean isUserEnable(User user) throws UserManagerException {

        return false;
    }

    @Override
    public void updateUsers(User user) throws UserManagerException {

    }

    @Override
    public void changePassword(User user, String newPassword) throws UserManagerException {

    }

    @Override
    public String encryptPassword(String password) throws UserManagerException {
        return DesUtil.sha512HexEncrypt(password);
    }


}
