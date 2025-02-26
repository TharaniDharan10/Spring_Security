package com.example.Spring_Security.controller;

import com.example.Spring_Security.model.MyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/Spring_Security")
    public String springSecurity(){
        return "Spring Security";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/user")    //if i want the username to be displayed when i hit this api,as the user is logged in, use 1st two lines in this method to fetch userdetails from securityContext(place were userDetails are stored)
    public String user(){

        //added this 2 lines to display username when this api is hit.Available in pdf
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser user = (MyUser) authentication.getPrincipal();   //getPrincipal gets POJO details of UserDetails
            return "hello ".concat(user.getUsername());
//        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }


    @PostMapping("/postAPI")
    public String postAPI(){
        return "postAPI";
    }
}
