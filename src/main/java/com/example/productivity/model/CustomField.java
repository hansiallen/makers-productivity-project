package com.example.productivity.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Custom_Fields")
public class CustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String customInfoKey;
    private String customInfoContent;

    CustomField(Long userProfileId,String customInfoKey,String customInfoContent){
        this.userId = userProfileId;
        this.customInfoKey = customInfoKey;
        this.customInfoContent = customInfoContent;
    }

    public CustomField(){
    }

    public Long getUserProfileId() {
        return userId;
    }

    public void setCustomInfoKey(String customInfoKey) {
        this.customInfoKey = customInfoKey;
    }

    public String getCustomInfoKey() {
        return customInfoKey;
    }

    public void setCustomInfoContent(String customInfoValue) {
        this.customInfoContent = customInfoValue;
    }

    public String getCustomInfoContent() {
        return customInfoContent;
    }

    public void setUserProfileId(Long userId) {
        this.userId = userId;
    }
}
