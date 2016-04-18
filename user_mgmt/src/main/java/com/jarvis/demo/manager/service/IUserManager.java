package com.jarvis.demo.manager.service;

import com.jarvis.users.IActiveUser;
import com.jarvis.users.User;

/**
 * Created by Jarvis on 4/14/16.
 */
public interface IUserManager {

    public User login(String userName, String password);

    public boolean isUserEnable(IActiveUser user);

    public void updateUsers(IActiveUser user);

}
