package com.GetApp.Get.Config;

import com.GetApp.Get.Dao.UserRepository;
import com.GetApp.Get.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
@Autowired
    UserRepository userRepository ;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.getUserByEmail(username) ;
        if(user==null){
            throw new UsernameNotFoundException("Couldn't found user ") ;
        }
        return new CustomUserDetail(user);
    }
}
