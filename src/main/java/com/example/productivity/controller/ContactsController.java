package com.example.productivity.controller;

import com.example.productivity.model.Contact;
import com.example.productivity.repository.ContactRepository;
import com.example.productivity.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ContactsController {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    CurrentUserController currentUser;

    @GetMapping("/contact/add/{id}")
    public ModelAndView addContact(@PathVariable int id) {
        Long idToAdd = (long) id;
        Long currentUserId = currentUser.getCurrentUser().getId();
        System.out.println(idToAdd);
        System.out.println(userProfileRepository.existsById(idToAdd));
        System.out.println(!idToAdd.equals(currentUserId));
        if (userProfileRepository.existsById(idToAdd) && !idToAdd.equals(currentUserId)) {
            Contact contact = new Contact(currentUserId, idToAdd);
            contactRepository.save(contact);
            return new ModelAndView("redirect:/profile/"+idToAdd);
        } else {
            ModelAndView err = new ModelAndView("core/error");
            err.addObject("errorMessage","failed to add contact");
            return err;
        }

    }
}
