package com.cts.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloWorldController {
    private final Log logger = LogFactory.getLog(getClass());
    
    String message = "Welcome to Spring MVC!";
 
    
    public String getMessage() {
        return message;
    }

    
    public void setMessage(String message) {
        this.message = message;
    }

    @RequestMapping("/hello.do")
    public ModelAndView showMessage(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        logger.info("in controller");
 
//        ModelAndView mv = new ModelAndView("helloworld");
//        ModelAndView mv = new ModelAndView(new RedirectView("/home", true));
//        ModelAndView mv = new ModelAndView("redirect:/home");
        ModelAndView mv = new ModelAndView("helloworld");
//        mv.addObject("message", message);
        mv.addObject("name", name);

//        return new ModelAndView("helloworld", "name", name);
        return mv;
    }
}