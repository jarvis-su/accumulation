package com.jarvis.demo.gui.controller.user;

import com.jarvis.demo.gui.controller.BasicController;
import com.jarvis.demo.manager.service.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Jarvis on 4/19/16.
 */
public abstract class UserBasicController  extends BasicController{

    @Autowired
    protected IUserManager userManager ;


}
