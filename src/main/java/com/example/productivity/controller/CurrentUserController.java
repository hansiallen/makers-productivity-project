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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
public class CurrentUserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserProfileRepository userProfileRepository;
    private final User currentUser = new User();

    @Autowired
    CloudinaryService cloudinaryService;

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

        UserProfile userProfile = userProfileRepository.findByUserId(user.getId());
        if (userProfile == null){
            userProfile = new UserProfile();
            userProfile.setUserId(user.getId());
            userProfileRepository.save(userProfile);
        }

        this.currentUser.setId(user.getId());
        return new RedirectView("/");
    }

    public User getCurrentUser(){return this.currentUser;}

    @GetMapping("/photoupload")
    public ModelAndView examplePage(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return new ModelAndView("core/upload_photo.html");
    }

    @PostMapping("uploadProfileImage")
    public ModelAndView uploadProfileImage(@RequestParam("profilePhoto") MultipartFile profilePhoto) {
        try {
            Long currentUserId = this.currentUser.getId();
            UserProfile userProfile = userProfileRepository.findByUserId(currentUserId);
            String uploadedUrl = cloudinaryService.uploadImage(profilePhoto);
            userProfile.setProfilePhotoUrl(uploadedUrl);
            userProfileRepository.save(userProfile);
            return new ModelAndView("core/upload_photo.html");
        } catch (Exception e) {
            return new ModelAndView("core/upload_photo.html");
        }
    }
}
