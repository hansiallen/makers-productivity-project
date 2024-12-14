package com.example.productivity.controller;


import com.example.productivity.model.UserLink;
import com.example.productivity.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class UserLinksController {

    @Autowired
    CurrentUserController currentUser;
    @Autowired
    UserLinkRepository userLinkRepository;

    @GetMapping("/userLink/add")
    public ModelAndView newUserLink(){

        ModelAndView modelAndView = new ModelAndView("profile/userLink/add");
        modelAndView.addObject("userLink", new UserLink());

        return modelAndView;
    }

    @PostMapping("/userLink/add")
    public RedirectView add(@ModelAttribute UserLink userLink){

        userLink.setUserId(currentUser.getCurrentUser().getId());
        userLinkRepository.save(userLink);
        return new RedirectView("/profile/"+currentUser.getCurrentUser().getId());
    }

    @GetMapping("/userLink/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView errView = new ModelAndView("core/error");

        UserLink userLinkToDelete = new UserLink();
        Optional<UserLink> userLinkToDeleteOptional = userLinkRepository.findById(id);

        if (userLinkToDeleteOptional.isEmpty()){

            errView.addObject("errorMessage","custom field does not exist");
            return errView;
        }
        else {
            userLinkToDelete = userLinkToDeleteOptional.get();
        }

        if(userLinkToDelete.getUserId() != currentUser.getCurrentUser().getId()){
            errView.addObject("errorMessage","You do not own this custom field");
            return errView;
        }
        else {
            userLinkRepository.deleteById(id);
            return new ModelAndView("redirect:/profile/update");
        }
    }

}
