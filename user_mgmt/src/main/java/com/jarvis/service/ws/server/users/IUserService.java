package com.jarvis.service.ws.server.users;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Jarvis on 4/11/16.
 */
@WebService(targetNamespace = "http://demo/userService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IUserService {

    @WebMethod
    String login(String userName, String password);
}
