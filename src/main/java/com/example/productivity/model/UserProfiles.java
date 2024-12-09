package com.example.productivity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS_PROFILES")
public class UserProfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String profilePhotoUrl;
    private String preferredName;
    public UserProfiles(Long userId, String firstName,String middleName,String lastName, String profilePhotoUrl, String preferredName) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.preferredName = preferredName;
    }

    public UserProfiles() {
    }

    public Long getId() {return this.id;}
    public void setId(Long id) {this.id = id;}

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
