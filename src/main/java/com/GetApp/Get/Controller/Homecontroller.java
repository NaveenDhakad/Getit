package com.GetApp.Get.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String signup(Model model) {
        model.addAttribute("formType" , "register") ;
        return "signupLogin" ;
    }
    @GetMapping("/login")
    public String Login(Model model) {
        model.addAttribute("formType" , "login") ;
        return "signupLogin" ;
    }


}
