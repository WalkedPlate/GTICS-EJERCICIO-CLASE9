package com.example.gticsejercicioclase7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = {"/openLoginWindow","","/"})
    public String loginForm(){
        return "loginWindow";
    }

}
