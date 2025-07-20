package com.GetApp.Get.Config;

import com.GetApp.Get.Dao.UserRepository;
import com.GetApp.Get.Entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OnAuthenticationSuccess implements AuthenticationSuccessHandler {
Logger logger = LoggerFactory.getLogger(OnAuthenticationSuccess.class) ;
@Autowired
private UserRepository userRepository ;

@Autowired
private BCryptPasswordEncoder passwordEncoder  ;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
logger.info("OnAuthenticationSuccess");
        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal() ;
//        logger.info(user.getName());
String email =user.getAttribute("email").toString() ;
String name =user.getAttribute("name").toString() ;

User user1 =new User( );
user1.setUsername(name);
user1.setEmail(email);
user1.setPassword(passwordEncoder.encode("oauth2user"));

  User user2= userRepository.getUserByEmail(email) ;
  if(user2==null){
      userRepository.save(user1) ;
  }

new DefaultRedirectStrategy().sendRedirect(request,response,"/");



    }
}
