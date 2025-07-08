package com.GetApp.Get.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class Homecontroller {

    @GetMapping("/")
    public String Home(){
        System.out.println("hello");
        return "index" ;
    }

}
