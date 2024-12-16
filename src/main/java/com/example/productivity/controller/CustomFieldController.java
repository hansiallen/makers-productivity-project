package com.example.productivity.controller;

import com.example.productivity.model.CustomField;
import com.example.productivity.model.UserProfile;
import com.example.productivity.repository.CustomFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;
import java.util.Optional;

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

        customField.setUserId(currentUser.getCurrentUser().getId());
        System.out.println(customField.getCustomInfoContent());
        customFieldRepository.save(customField);
        return new RedirectView("/profile/"+currentUser.getCurrentUser().getId());
    }

    @GetMapping("/customField/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){

        ModelAndView errView = new ModelAndView("core/error");

        CustomField customFieldToDelete = new CustomField();
        Optional<CustomField> customFieldToDeleteOptional = customFieldRepository.findById(id);

        if (customFieldToDeleteOptional.isEmpty()){

            errView.addObject("errorMessage","custom field does not exist");
            return errView;
        }
        else {
            customFieldToDelete = customFieldToDeleteOptional.get();
        }

        if(!Objects.equals(customFieldToDelete.getUserId(), currentUser.getCurrentUser().getId())){
            errView.addObject("errorMessage","You do not own this custom field");
            return errView;
        }
        else {
            customFieldRepository.deleteById(id);
            return new ModelAndView("redirect:/profile/update");
        }


    }

}
