package com.GetApp.Get.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class MyCofig {
    @Autowired
    @Lazy
    private OnAuthenticationSuccess onAuthenticationSuccess ;

    @Bean
    public UserDetailsService getUserDetailService(){
        return  new UserDetailsServiceImpl()  ;

       }
       @Bean
       public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder() ;
       }

       @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider() ;
        provider.setUserDetailsService(getUserDetailService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider ;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider()) // ✅ Add this line
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());


        http.oauth2Login(oauth->{oauth.loginPage("/login") ;
            oauth.successHandler(
                    onAuthenticationSuccess ) ;
            }) ;
        return http.build();
    }




}
