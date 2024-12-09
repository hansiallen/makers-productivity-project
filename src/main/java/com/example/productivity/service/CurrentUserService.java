package com.example.productivity.service;

import com.example.productivity.model.User;
import com.example.productivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

public class CurrentUserService {

    @Autowired
    public UserRepository userRepository;
    private final Map<String, Object> userDetails;

    public CurrentUserService() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser principal = (DefaultOidcUser) auth.getPrincipal();
        this.userDetails = principal.getAttributes();

    }

    public String getEmail() {
        return this.userDetails.get("email").toString();
    }
    public String getAuth0id(){
        return this.userDetails.get("sub").toString();
    }

    public void handleLogin(){

        User user = userRepository.findByAuth0Id(getAuth0id());
        if (user == null){

            user = new User();
            user.setAuth0Id(getAuth0id());
            user.setEmail(getEmail());
            userRepository.save(user);
        }

    }



}

