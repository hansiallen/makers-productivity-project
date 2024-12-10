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
    private String email;
    private Long id;
    private String auth0id;

    @GetMapping("users/after-login")
    public RedirectView handleLogin(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser principal = (DefaultOidcUser) auth.getPrincipal();
        Map<String, Object> userDetails = principal.getAttributes();

        this.email = userDetails.get("email").toString();
        this.auth0id = userDetails.get("sub").toString();

        User user = userRepository.findByAuth0Id(getAuth0id());
        if (user == null){
            user = new User();
            user.setAuth0Id(getAuth0id());
            user.setEmail(getEmail());
            userRepository.save(user);
        }

        this.id = user.getId();
        return new RedirectView("/");
    }

    public String getEmail() {return this.email;}
    public Long getId(){return this.id;}
    public String getAuth0id(){return this.auth0id;}

}
