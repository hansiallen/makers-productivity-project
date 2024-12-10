package com.example.productivity.controller;


import com.example.productivity.model.User;
import com.example.productivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
public class CurrentUserController {

    @Autowired
    UserRepository userRepository;
    private final User currentUser = new User();

    @GetMapping("users/after-login")
    public RedirectView handleLogin(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser principal = (DefaultOidcUser) auth.getPrincipal();
        Map<String, Object> userDetails = principal.getAttributes();

        this.currentUser.setEmail(userDetails.get("email").toString());
        this.currentUser.setAuth0Id(userDetails.get("sub").toString());

        User user = userRepository.findByAuth0Id(currentUser.getAuth0Id());
        if (user == null){
            user = new User();
            user.setAuth0Id(currentUser.getAuth0Id());
            user.setEmail(currentUser.getEmail());
            userRepository.save(user);
        }
        this.currentUser.setId(user.getId());
        return new RedirectView("/");
    }

    public User getCurrentUser(){return this.currentUser;}
}
