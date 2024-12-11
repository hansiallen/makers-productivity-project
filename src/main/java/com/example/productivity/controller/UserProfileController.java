package com.example.productivity.controller;

import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    CurrentUserController currentUser;


    @GetMapping("/profile")
    public ModelAndView profile(){

        Long currentUserId = currentUser.getCurrentUser().getId();
        UserProfile currentUserProfile = userProfileRepository.findByUserId(currentUserId);

        ModelAndView modelAndView = new ModelAndView("profile/update");
        modelAndView.addObject("currentUser",currentUser.getCurrentUser());
        modelAndView.addObject("userProfile", currentUserProfile);

        if(currentUserProfile == null){
            currentUserProfile = new UserProfile();
            currentUserProfile.setUserId(currentUserId);
            userProfileRepository.save(currentUserProfile);
        }

        return modelAndView;
    }


    @PostMapping("/profile/update")
    public RedirectView create(@ModelAttribute UserProfile userProfile){

        UserProfile currentUserProfile = userProfileRepository.findByUserId(currentUser.getCurrentUser().getId());
        userProfile.setUserId(currentUserProfile.getUserId());
        userProfile.setUserId(currentUser.getCurrentUser().getId());
        userProfileRepository.save(userProfile);

        System.out.println(userProfile.getUserId());
        return new RedirectView("/profile");
    }


}
