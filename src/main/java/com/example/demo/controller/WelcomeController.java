package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
    @RequestMapping("/welcome")
    public String welcome(){
        return "index.html";
    }
    @RequestMapping("/product")
    public String productPage(){
        return "product.html";
    }
}
