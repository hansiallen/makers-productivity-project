package com.example.productivity.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "USER_PROFILES")
public class UserProfile {

    @Id
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String profilePhotoUrl;
    private String preferredName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
  
    public UserProfile(Long userId, String firstName, String middleName, String lastName, String profilePhotoUrl, String preferredName, String email, String phoneNumber, LocalDate birthday) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.preferredName = preferredName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public UserProfile() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public String getPhoneNumber(){ return phoneNumber;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public LocalDate getBirthday(){return birthday;}

    public void setBirthday(LocalDate birthday){this.birthday = birthday;}
}
