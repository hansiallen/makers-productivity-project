package com.example.productivity.controller;

import com.example.productivity.model.CustomField;
import com.example.productivity.model.User;
import com.example.productivity.model.UserLink;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.ContactRepository;
import com.example.productivity.repository.CustomFieldRepository;
import com.example.productivity.repository.UserLinkRepository;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    ContactRepository contactRepository;
    @Autowired
    UserLinkRepository userLinkRepository;

    @GetMapping("/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Long id){

        Long currentUserId = currentUser.getCurrentUser().getId();
        ModelAndView modelAndView = new ModelAndView("profile/show");
        UserProfile userProfile = userProfileRepository.findByUserId(id);
        List<CustomField> customFields = customFieldRepository.findByUserId(id);
        List<UserLink> userLinks = userLinkRepository.findByUserId(id);
        Boolean inContacts = contactRepository.usersInContacts(currentUserId,id);

        if (userProfile == null){
            modelAndView = new ModelAndView("core/error");
            modelAndView.addObject("errorMessage","User does not exist");
            return modelAndView;
        }

        boolean currUserIsViewingOwnProfile = id.equals(currentUser.getCurrentUser().getId());
        modelAndView.addObject("userProfile",userProfile);

        modelAndView.addObject("customFields",customFields);
        modelAndView.addObject("customField", new CustomField());

        modelAndView.addObject("userLinks",userLinks);
        modelAndView.addObject("userLink",new UserLink());

        modelAndView.addObject("currUserIsViewingOwnProfile",currUserIsViewingOwnProfile);
        modelAndView.addObject("userInContacts",inContacts);

        System.out.println(inContacts);
        return modelAndView;
    }

    @GetMapping("/profile/me")
    public RedirectView viewMyProfile(){

        return new RedirectView("/profile/"+currentUser.getCurrentUser().getId());

    }




    @PostMapping("/profile/update")
    public RedirectView create(@ModelAttribute UserProfile userProfile){

        UserProfile currentUserProfile = userProfileRepository.findByUserId(currentUser.getCurrentUser().getId());
        userProfile.setUserId(currentUserProfile.getUserId());
        userProfile.setUserId(currentUser.getCurrentUser().getId());
        userProfileRepository.save(userProfile);

        System.out.println(userProfile.getUserId());
        return new RedirectView("/profile/"+userProfile.getUserId().toString());
    }


}
