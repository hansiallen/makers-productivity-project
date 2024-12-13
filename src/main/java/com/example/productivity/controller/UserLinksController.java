package com.example.productivity.controller;


import com.example.productivity.model.UserLink;
import com.example.productivity.repository.UserLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

}
