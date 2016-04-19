package com.jarvis.demo.gui.controller.user;

import com.jarvis.demo.gui.ForwardURL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jarvis on 4/19/16.
 */
@Controller
public class UserLogin extends UserBasicController {

    @RequestMapping("/login")
    private String login(){
        userManager.login("","");
        return ForwardURL.MAIN;
    }
}
