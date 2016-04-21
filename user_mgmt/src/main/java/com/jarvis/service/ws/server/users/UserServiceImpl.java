package com.jarvis.service.ws.server.users;

import com.jarvis.demo.manager.service.IUserManager;
import com.jarvis.users.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;

/**
 * Created by Jarvis on 4/11/16.
 */
@WebService(endpointInterface = "com.jarvis.service.ws.server.users.IUserService", targetNamespace = "http://demo/userService")

public class UserServiceImpl implements IUserService {

    @Autowired
    IUserManager userManager;

    @Override
    public String login(String userName, String password) {
        User user = userManager.login(userName, password);
        return user.getLoginName();
    }
}
