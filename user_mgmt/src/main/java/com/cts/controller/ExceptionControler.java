package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cts.domain.Product;
import com.cts.service.IProductService;

@Controller
public class ExceptionControler extends BaseController{

    @Autowired
    @Qualifier("productServiceImpl")
    private IProductService productService;

    @RequestMapping("/exception")
    public ModelAndView view(@RequestParam int pid) throws Exception {
        Product product = productService.getProductById(pid);
        ModelAndView mv = new ModelAndView("success");
        mv.addObject(product);
        return mv;
    }
}
