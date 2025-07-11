package com.GetApp.Get.Service;

import com.GetApp.Get.Dao.UserRepository;
import com.GetApp.Get.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
private UserRepository userRepository ;

    public String registerCheckEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already in use!";
        }

        userRepository.save(user);
        return "User registered successfully";
    }
}

