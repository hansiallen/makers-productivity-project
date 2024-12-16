package com.example.productivity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_LINKS")
public class UserLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String websiteLinkName;
    private String websiteLinkUrl;

    public UserLink(){

    }
    public UserLink(Long userId, String websiteLinkName, String websiteLinkUrl){
        this.userId=userId;
        this.websiteLinkName=websiteLinkName;
        this.websiteLinkUrl = websiteLinkUrl;
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
        String url = this.websiteLinkUrl;
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
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
        this.websiteLinkUrl = websiteLinkUrl;
    }
}
