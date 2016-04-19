package com.jarvis.demo.gui.controller;

import com.jarvis.demo.gui.ForwardURL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jarvis on 4/19/16.
 */
@Controller
public class Home extends BasicController{

    @RequestMapping("/home")
    public String home() {
        logger.info("HomeController: passing through.....");
        return ForwardURL.HOME;
    }
}
