package com.example.productivity.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACTS")
public class Contact {

    // Attributes
    @Id
    // The user who is adding the contact
    private Long user_id1;
    // The contact who is being added
    private Long user_id2;

    // Constructors
    public Contact(){}
    public Contact(Long user_id1, Long user_id2){
        this.user_id1 = user_id1;
        this.user_id2 = user_id2;
    }

    // Getters/ setters
    public Long getUserId1() {return this.user_id1;}
    public void setUserId1(Long user_id1) {this.user_id1 = user_id1;}

    public Long getUserId2(){return this.user_id2;}
    public void setUserId2(Long user_id2) {this.user_id2 = user_id2;}

}
