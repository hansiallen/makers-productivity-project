package com.example.productivity.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

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
  
    public UserProfile(Long userId, String firstName, String middleName, String lastName, String profilePhotoUrl, String preferredName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.preferredName = preferredName;
        this.email = email;
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
}
