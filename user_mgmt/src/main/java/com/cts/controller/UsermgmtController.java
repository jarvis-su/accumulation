package com.cts.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cts.domain.User;

@Controller
@RequestMapping("/usermgmt")
public class UsermgmtController {

    @RequestMapping(method = RequestMethod.GET, params = "new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "user/edit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addForm(@Valid User user, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("br", br);
            return "user/edit";
        }
        // userService.saveUser(user);
        return "redirect:/usermgmt/list/" + user.getUserName() + "?param=show";
    }

    @RequestMapping(value = "/list/{username}", method = RequestMethod.GET, params="param=show")
    public String showUser(@PathVariable("username") String name, Model model) {
        model.addAttribute("username", name);
        return "home";
    }
}
