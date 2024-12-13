package com.example.productivity.controller;

import com.example.productivity.model.CustomField;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.CustomFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class CustomFieldController {

    @Autowired
    CurrentUserController currentUser;
    @Autowired
    CustomFieldRepository customFieldRepository;

    @GetMapping("/customField/add")
    public ModelAndView newCustomFieldForm(){

        ModelAndView modelAndView = new ModelAndView("profile/customField/add");
        modelAndView.addObject("customField", new CustomField());

        return modelAndView;
    }

    @PostMapping("/customField/add")
    public RedirectView add(@ModelAttribute CustomField customField){

        customField.setUserProfileId(currentUser.getCurrentUser().getId());
        System.out.println(customField.getCustomInfoContent());
        customFieldRepository.save(customField);
        return new RedirectView("/profile/"+currentUser.getCurrentUser().getId());
    }

}
