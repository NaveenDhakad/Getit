package com.GetApp.Get.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyCofig {
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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/signup", "/register", "/css/**", "/js/**", "/images/**").permitAll() // Public URLs
                        .requestMatchers("/user/**").hasRole("USER") // Protected URLs
                        .anyRequest().authenticated() // Everything else requires login
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

        return http.build();
    }


}
