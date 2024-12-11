package com.example.productivity.controller;

import com.example.productivity.model.User;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    CurrentUserController currentUser;

    @GetMapping("/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Long id){

        ModelAndView modelAndView = new ModelAndView("profile/show");
        UserProfile userProfile = userProfileRepository.findByUserId(id);

        if (userProfile == null){
            modelAndView = new ModelAndView("core/error");
            modelAndView.addObject("errorMessage","User does not exist");
            return modelAndView;
        }

        boolean currUserIsViewingOwnProfile = id.equals(currentUser.getCurrentUser().getId());
        modelAndView.addObject("userProfile",userProfile);
        modelAndView.addObject("currUserIsViewingOwnProfile",currUserIsViewingOwnProfile);

        return modelAndView;

    }


    @PostMapping("/profile/update")
    public RedirectView create(@ModelAttribute UserProfile userProfile){

        UserProfile currentUserProfile = userProfileRepository.findByUserId(currentUser.getCurrentUser().getId());
        userProfile.setId(currentUserProfile.getId());
        userProfile.setUserId(currentUser.getCurrentUser().getId());
        userProfileRepository.save(userProfile);

        System.out.println(userProfile.getUserId());
        return new RedirectView("/profile/"+userProfile.getUserId().toString());
    }


}
