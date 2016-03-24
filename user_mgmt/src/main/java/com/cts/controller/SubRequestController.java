package com.cts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SubRequestController extends BaseController{

    @RequestMapping("/request2")
    public String view(){
        // request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // session = request.getSession();
        request.setAttribute("result", "request2");
        // session.setAttribute("result", "session2");
        return "result";
    }
}
