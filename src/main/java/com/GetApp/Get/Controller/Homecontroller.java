package com.GetApp.Get.Controller;
import com.GetApp.Get.Dao.UserRepository;
import com.GetApp.Get.Entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Homecontroller {
    @Autowired
    private UserRepository userRepository ;


    @GetMapping("/")
    public String Home()
    {
        return "index" ;
    }

    @GetMapping("/signup")
    public String signup(Model model ) {
        model.addAttribute("loginData" , new User()) ;
        model.addAttribute("formType" , "register") ;
        return "signupLogin" ;
    }
    @GetMapping("/login")
    public String Login(Model model) {
        model.addAttribute("formType" , "login") ;
        return "signupLogin" ;
    }
    @PostMapping("/register")
public String RegisterHandle(@Valid @ModelAttribute("loginData") User user , Model model , BindingResult result) {
if(result.hasErrors()){
    model.addAttribute("formType", "register");
    System.out.println(result);
    return "signupLogin" ;
}
if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("formType", "register");
            model.addAttribute("loginData", user);
            model.addAttribute("emailError", "Email already registered");
            return "signupLogin";
}
userRepository.save(user) ;
        return "index" ;
}


}
