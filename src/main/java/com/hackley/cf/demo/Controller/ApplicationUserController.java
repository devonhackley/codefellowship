package com.hackley.cf.demo.Controller;

import com.hackley.cf.demo.Repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String getLandingPage(){
        return "landing";
    }

}
