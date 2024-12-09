package com.example.productivity.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Custom_Field")
public class CustomField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userProfileId;
    private String customInfoKey;
    private String customInfoValue;

    CustomField(Long id, Long userProfileId,String customInfoKey,String customInfoValue){
        this.id = id;
        this.userProfileId = userProfileId;
        this.customInfoKey = customInfoKey;
        this.customInfoValue = customInfoValue;
    }

    CustomField(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setCustomInfoKey(String customInfoKey) {
        this.customInfoKey = customInfoKey;
    }

    public String getCustomInfoKey() {
        return customInfoKey;
    }

    public void setCustomInfoValue(String customInfoValue) {
        this.customInfoValue = customInfoValue;
    }

    public String getCustomInfoValue() {
        return customInfoValue;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }
}
