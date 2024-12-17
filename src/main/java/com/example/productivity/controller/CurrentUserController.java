package com.example.productivity.controller;


import com.example.productivity.model.User;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.repository.UserRepository;
import com.example.productivity.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class CurrentUserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("users/after-login")
    public RedirectView handleLogin() {
        User user = getCurrentUser();


        UserProfile userProfile = userProfileRepository.findByUserId(user.getId());
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUserId(user.getId());
            userProfile.setEmail(user.getEmail());
            userProfileRepository.save(userProfile);
            return new RedirectView("/profile/update");
        }

        return new RedirectView("/");
    }

    public User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof DefaultOidcUser)) {
            throw new IllegalStateException("No authenticated user found");
        }

        DefaultOidcUser principal = (DefaultOidcUser) auth.getPrincipal();
        String auth0Id = principal.getAttributes().get("sub").toString();
        String email = principal.getAttributes().get("email").toString();

        User user = userRepository.findByAuth0Id(auth0Id);
        if (user == null) {
            user = new User();
            user.setAuth0Id(auth0Id);
            user.setEmail(email);
            userRepository.save(user);
        }

        return user;
    }
}
