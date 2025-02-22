package com.example.Spring_Security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/Spring_Security")
    public String springSecurity(){
        return "Spring Security";
    }
}
