package com.example.productivity.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACTS")
public class Contact {

    // Attributes
    @Id
    private Long id;
    // The user who is adding the contact
    private Long user_id1;
    // The contact who is being added
    private Long user_id2;
    private boolean isFavourite;

    // Constructors
    public Contact(){}
    public Contact(Long user_id1, Long user_id2){
        this.user_id1 = user_id1;
        this.user_id2 = user_id2;
    }
    public Contact(Long user_id1, Long user_id2, boolean isFavourite){
        this.user_id1 = user_id1;
        this.user_id2 = user_id2;
        this.isFavourite = isFavourite;
    }

    // Getters/ setters
    public Long getId() {return this.id;}
    public void setId(Long id) {this.id = id;}

    public Long getUserId1() {return this.user_id1;}
    public void setUserId1(Long user_id1) {this.user_id1 = user_id1;}

    public Long getUserId2() {return this.user_id2;}
    public void setUserId2(Long user_id2) {this.user_id2 = user_id2;}

    public boolean getIsFavourite() {return this.isFavourite;}
    public void setIsFavourite(boolean isFavourite) {this.isFavourite = isFavourite;}
}
