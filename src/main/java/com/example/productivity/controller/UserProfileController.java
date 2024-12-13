package com.example.productivity.controller;

import com.example.productivity.model.CustomField;
import com.example.productivity.model.User;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.CustomFieldRepository;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    CustomFieldRepository customFieldRepository;
    @Autowired
    CurrentUserController currentUser;
    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping("/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Long id){

        ModelAndView modelAndView = new ModelAndView("profile/show");
        UserProfile userProfile = userProfileRepository.findByUserId(id);
        List<CustomField> customFields = customFieldRepository.findByUserId(id);

        if (userProfile == null){
            modelAndView = new ModelAndView("core/error");
            modelAndView.addObject("errorMessage","User does not exist");
            return modelAndView;
        }

        boolean currUserIsViewingOwnProfile = id.equals(currentUser.getCurrentUser().getId());
        modelAndView.addObject("userProfile",userProfile);
        modelAndView.addObject("customFields",customFields);
        modelAndView.addObject("currUserIsViewingOwnProfile",currUserIsViewingOwnProfile);

        return modelAndView;
    }


    @PostMapping("/profile/update")
    public RedirectView create(@ModelAttribute UserProfile userProfile, @RequestParam("profilePhoto") MultipartFile profilePhoto){

        UserProfile currentUserProfile = userProfileRepository.findByUserId(currentUser.getCurrentUser().getId());
        userProfile.setUserId(currentUserProfile.getUserId());
        userProfile.setUserId(currentUser.getCurrentUser().getId());

        if (!profilePhoto.isEmpty()) {
            String uploadedUrl = cloudinaryService.uploadImage(profilePhoto);
            userProfile.setProfilePhotoUrl(uploadedUrl);
        } else {
            userProfile.setProfilePhotoUrl(currentUserProfile.getProfilePhotoUrl());
        }

        userProfileRepository.save(userProfile);

        System.out.println(userProfile.getUserId());
        return new RedirectView("/profile/"+userProfile.getUserId().toString());
    }


}
