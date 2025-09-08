package com.supriyo.urlShortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        // This will serve src/main/resources//index.html
        return "forward:/index.html";
    }
}
