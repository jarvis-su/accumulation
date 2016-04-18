package com.jarvis.service.ws.server.users;

import javax.jws.WebService;

/**
 * Created by Jarvis on 4/11/16.
 */
@WebService(endpointInterface = "com.jarvis.service.ws.server.users.IUserService", targetNamespace = "http://demo/userService")

public class UserServiceImpl implements IUserService {

    @Override
    public String login(String userName, String password) {
        return "hello";
    }
}
