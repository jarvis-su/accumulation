package com.jarvis.demo.manager.service;

import com.jarvis.users.IActiveUser;

/**
 * Created by Jarvis on 4/14/16.
 */
public interface IUserManager {

    public IActiveUser login(String userName, String password);

    public boolean isUserEnable(IActiveUser user);

    public void updateUsers(IActiveUser user);

}
