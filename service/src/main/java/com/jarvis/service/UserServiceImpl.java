package com.jarvis.service;

import javax.jws.WebService;

/**
 * UserServiceImpl
 *
 * @author Jarvis Su
 * @date 4/11/2016
 */
@WebService(endpointInterface = "com.jarvis.service.IUserService", targetNamespace = "http://demo/userService")
public class UserServiceImpl implements IUserService {

    @Override
    public String sayHello() {
        return "hello ";
    }
}
