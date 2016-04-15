package com.cts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.domain.Product;

@Controller
public class AjaxController {

    @RequestMapping("/ajax/view")
    public String view(){
        return "ajax";
    }
    
    @RequestMapping("/ajax/products")
    @ResponseBody
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        
        Product p1 = new Product();
        p1.setPrice(22.0);
        p1.setDescription("football");
        products.add(p1);
        
        Product p2 = new Product();
        p2.setPrice(33.3);
        p2.setDescription("JavaScript: The Good Parts");
        products.add(p2);
        
        return products;
    }
}
