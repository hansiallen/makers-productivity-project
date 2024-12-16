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
    private String profilePhotoUrl = "https://res.cloudinary.com/dls9qdz5e/image/upload/v1734345275/zqhhrjazmtrmmzmxmswt.png";
    private String preferredName;
  
    public UserProfile(Long userId, String firstName, String middleName, String lastName, String profilePhotoUrl, String preferredName) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        if (profilePhotoUrl != null){
            this.profilePhotoUrl = profilePhotoUrl;
        }
        this.preferredName = preferredName;
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
}
