package com.cts.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cts.domain.Product;
import com.cts.service.IProductService;

@Controller
public class InventoryController {

    @Autowired
    // @Qualifier("simpleProductServiceImpl")
    @Qualifier("productServiceImpl")
    private IProductService productService;
    private final Log logger = LogFactory.getLog(getClass());

    
    @RequestMapping(value = "/inventory/p_view")
    public String view(@ModelAttribute Product product) {
        return "inventory/p_info";
    }

    @RequestMapping(value = "/inventory/p_add", method = RequestMethod.POST)
    public String add(Model model,@ModelAttribute("product") @Valid Product product, BindingResult result ) {
        if (result.hasErrors()) {
            logger.error("Add product occured errors.");
            model.addAttribute("product", product);
            return "inventory/p_info";
        }
        
        logger.info("Add product successful.");
//        return "redirect:/inventory/p_list";
        return "forward:/inventory/p_list";
    }

    @RequestMapping(value = "/inventory/p_list")
    public String list(Model model) {
        List<Product> products = productService.getProducts();
        logger.info("get product list successfuly.");

        model.addAttribute("products", products);
        return "inventory/p_list";
    }

}
