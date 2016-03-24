package com.cts.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ScopeController {

    @RequestMapping("/request")
    public String scopeMethod1(Model model, ServletRequest request){
        request.setAttribute("result", "requestScope");
        model.addAttribute("attr", "attr----");
        return "result";
    }
    
    @RequestMapping("/session")
    public String scopeMethod2(HttpSession session){
        session.setAttribute("result", "username from session");
        return "result";
    }
    
    @RequestMapping("/response")
    public String scopeMethod2(ServletResponse response){
        System.out.println(response.hashCode());
        return "result";
    }
    
    
}
