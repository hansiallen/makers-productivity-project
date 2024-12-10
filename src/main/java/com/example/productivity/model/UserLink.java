package com.example.productivity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS_LINKS")
public class UserLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userProfileId;
    private String websiteLinkName;
    private String WebsiteLinkUrl;

    UserLink(){

    }
    UserLink(Long userProfileId, String websiteLinkName, String websiteLinkUrl){
        this.userProfileId=userProfileId;
        this.websiteLinkName=websiteLinkName;
        this.WebsiteLinkUrl = websiteLinkUrl;
    }

    public Long getuserProfileId() {
        return userProfileId;
    }

    public Long getId() {
        return id;
    }

    public String getWebsiteLinkName() {
        return websiteLinkName;
    }

    public String getWebsiteLinkUrl() {
        return WebsiteLinkUrl;
    }

    public void setuserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWebsiteLinkName(String websiteLinkName) {
        this.websiteLinkName = websiteLinkName;
    }

    public void setWebsiteLinkUrl(String websiteLinkUrl) {
        WebsiteLinkUrl = websiteLinkUrl;
    }
}
