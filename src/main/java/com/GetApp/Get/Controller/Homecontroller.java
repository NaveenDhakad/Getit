package com.GetApp.Get.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Homecontroller {

    @GetMapping("/")
    public String Home()
    {
        return "index" ;
    }

    @GetMapping("/signup")
    public String signup() {
    return "signupLogin" ;
    }


}
