package com.jarvis.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by C5023792 on 4/11/2016.
 */
@WebService(targetNamespace = "http://demo/userService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface IUserService {
    @WebMethod
    String sayHello();
}
