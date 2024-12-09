package com.example.productivity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS_LINKS")
public class UserLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String websiteLinkName;
    private String WebsiteLinkUrl;

    UserLink(){

    }
    UserLink(Long id, Long userId, String websiteLinkName, String websiteLinkUrl){
        this.id=id;
        this.userId=userId;
        this.websiteLinkName=websiteLinkName;
        this.WebsiteLinkUrl = websiteLinkUrl;
    }

    public Long getUserId() {
        return userId;
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

    public void setUserId(Long userId) {
        this.userId = userId;
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
