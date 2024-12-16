package com.example.productivity.controller;


import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeProvider {
    @Autowired
    private CurrentUserController currentUser;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @ModelAttribute("currentUser") //Adds the attribute current user to every template
    public UserProfile addCurrentUserToModel() {
        return userProfileRepository.findByUserId(currentUser.getCurrentUser().getId());
    }
}
