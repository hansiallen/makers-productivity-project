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
    private Map<String, Object> userDetails;

    @GetMapping("users/after-login")
    public RedirectView handleLogin(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser principal = (DefaultOidcUser) auth.getPrincipal();
        this.userDetails = principal.getAttributes();

        User user = userRepository.findByAuth0Id(getAuth0id());
        if (user == null){

            user = new User();
            user.setAuth0Id(getAuth0id());
            user.setEmail(getEmail());
            userRepository.save(user);
        }
        return new RedirectView("/");
    }

    public String getEmail() {
        return this.userDetails.get("email").toString();
    }
    public String getAuth0id(){
        return this.userDetails.get("sub").toString();
    }

}
