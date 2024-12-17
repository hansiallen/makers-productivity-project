package com.example.productivity.controller;

import com.example.productivity.model.Contact;
import com.example.productivity.model.RollingCode;
import com.example.productivity.repository.ContactRepository;
import com.example.productivity.repository.RollingCodeRepository;
import com.example.productivity.repository.UserProfileRepository;
import com.example.productivity.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

@RestController
public class ContactsController {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    CurrentUserController currentUser;

    @Autowired
    NotificationsService notificationsService;

    @Autowired
    RollingCodeRepository rollingCodeRepository;

    @GetMapping("/contact/add/{id}")
    public ModelAndView addContact(@PathVariable int id) {
        Long idToAdd = (long) id;
        Long currentUserId = currentUser.getCurrentUser().getId();
        System.out.println(idToAdd);
        System.out.println(userProfileRepository.existsById(idToAdd));
        System.out.println(!idToAdd.equals(currentUserId));
        if (userProfileRepository.existsById(idToAdd) && !idToAdd.equals(currentUserId)) {
            Contact contact = new Contact(currentUserId, idToAdd);
            contact.setUserId1(currentUserId);
            contact.setUserId2(idToAdd);
            contactRepository.save(contact);
            notificationsService.userAddsContactNotification(currentUserId, idToAdd);
            notificationsService.userIsAddedAsContactNotification(currentUserId, idToAdd);
            return new ModelAndView("redirect:/profile/"+idToAdd);
        } else {
            ModelAndView err = new ModelAndView("core/error");
            err.addObject("errorMessage","failed to add contact");
            return err;
        }
    }

    boolean codeIsValid(Long code) {
        boolean exists = rollingCodeRepository.existsByCode(code);
        if (exists) {
            System.out.println("Exists!");
            Timestamp currentTime = Timestamp.from(Instant.now());
            System.out.println(currentTime);
            RollingCode codeObject = rollingCodeRepository.findById(code).get();
            Timestamp comparisonTime = codeObject.getExpiryTime();
            System.out.println(comparisonTime);
            return comparisonTime.after(currentTime);
        }
        return false;
    }

    Long codeToId(Long code) {
        return rollingCodeRepository.findById(code).get().getUserId();
    }

    @GetMapping("/contact/add/json/{code}")
    public String addContactReturnJSON(@PathVariable Long code) {
        boolean isValid = codeIsValid(code);
        if (!isValid) {
            return "{\"success\": false, \"error\": \"Invalid code. Please regenerate code again and retry. The code may have expired.\"}";
        }
        Long idToAdd = codeToId(code);
        Long currentUserId = currentUser.getCurrentUser().getId();
        System.out.println(idToAdd);
        System.out.println(userProfileRepository.existsById(idToAdd));
        System.out.println(!idToAdd.equals(currentUserId));
        if (userProfileRepository.existsById(idToAdd) && !idToAdd.equals(currentUserId)) {
            Contact contact = new Contact(currentUserId, idToAdd);
            contact.setUserId1(currentUserId);
            contact.setUserId2(idToAdd);
            contactRepository.save(contact);
            notificationsService.userAddsContactNotification(currentUserId, idToAdd);
            notificationsService.userIsAddedAsContactNotification(currentUserId, idToAdd);
            return "{\"success\": true, \"id\": " + idToAdd + '}';
        } else {
            return "{\"success\": false}";
        }
    }
  
    
    @PostMapping("/contact/remove/{id}")
    public RedirectView removeContact(@PathVariable int id) {
        Long currentUserId = currentUser.getCurrentUser().getId();
        contactRepository.deleteByUserId1AndUserId2(currentUserId, (long) id);
        return new RedirectView("/");
    }

    Long generateShareCodeInt() {
        Random random = new Random();
        Long randomCode = (long) random.nextInt(99999999);
        if (rollingCodeRepository.existsById(randomCode)) {
            randomCode = generateShareCodeInt();
        }
        return randomCode;
    }

    @GetMapping("/get-share-code")
    public String getShareCode() {
        Long currentUserId = currentUser.getCurrentUser().getId();
        Instant currentInstant = Instant.now();
        Instant expiryInstant = currentInstant.plus(1, ChronoUnit.HOURS);
        Timestamp expiryTime = Timestamp.from(expiryInstant);
        Long code = generateShareCodeInt();
        RollingCode rollingCode = new RollingCode(code, currentUserId, expiryTime);
        rollingCodeRepository.save(rollingCode);
        return rollingCode.getCode().toString();
    }

    @GetMapping("/CAR/{id}")
    public RedirectView addContactAndRedirect(@PathVariable Long code) {
        Long idToAdd = codeToId(code);
        Long currentUserId = currentUser.getCurrentUser().getId();
        System.out.println(idToAdd);
        System.out.println(userProfileRepository.existsById(idToAdd));
        System.out.println(!idToAdd.equals(currentUserId));
        if (userProfileRepository.existsById(idToAdd) && !idToAdd.equals(currentUserId)) {
            Contact contact = new Contact(currentUserId, idToAdd);
            contact.setUserId1(currentUserId);
            contact.setUserId2(idToAdd);
            contactRepository.save(contact);
            notificationsService.userAddsContactNotification(currentUserId, idToAdd);
            notificationsService.userIsAddedAsContactNotification(currentUserId, idToAdd);
            return new RedirectView("/profile/" + idToAdd);
        } else {
            return new RedirectView("/profile/error");
        }
    }

    @GetMapping("/profile/error")
    public ModelAndView showProfileNotExistsErrorPage() {
        ModelAndView modelAndView = new ModelAndView("/profile/notFound.html");
        return modelAndView;
    }

    @PostMapping("/favourites/remove/{id}")
    public ResponseEntity<?> removeContactAsFavourite(@PathVariable Long id) {
        Contact contactToRemoveAsFavourite = contactRepository.findContactByUserId1AndUserId2(currentUser.getCurrentUser().getId(), id);
        if (contactToRemoveAsFavourite != null) {
            contactToRemoveAsFavourite.setIsFavourite(false);
            contactRepository.save(contactToRemoveAsFavourite);
            return ResponseEntity.ok().build(); // Return 200 OK
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error removing favourite");
    }

    @PostMapping("/favourites/add/{id}")
    public ResponseEntity<?> addContactAsFavourite(@PathVariable Long id) {
        Contact contactToAddAsFavourite = contactRepository.findContactByUserId1AndUserId2(currentUser.getCurrentUser().getId(), id);
        if (contactToAddAsFavourite != null) {
            contactToAddAsFavourite.setIsFavourite(true);
            contactRepository.save(contactToAddAsFavourite);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding favourite");
    }
}
