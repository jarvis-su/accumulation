package com.cts.controller;

import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    Comparator<String> comparator;

    @RequestMapping("/home")
    public String home() {
        logger.info("HomeController: passing through.....");
        return "home";
    }

    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public String compare(String input1, String input2, Model model) {
        int result = comparator.compare(input1, input2);
        String inEnglish = result < 0 ? "less than" : result > 0 ? "greater than" : "equal to";
        String output = "According to the comparator, '" + input1 + "' is " + inEnglish + " '" + input2 + "'";
        model.addAttribute("output", output);
        return "result/compareResult";
    }

}
