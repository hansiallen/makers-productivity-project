package com.example.productivity.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACTS")
public class Contact {

    // Attributes
    @Id
    private Long user_id1;
    private Long user_id2;

    // Constructors
    public Contact(){}
    public Contact(Long user_id1, Long user_id2){
        this.user_id1 = user_id1;
        this.user_id2 = user_id2;
    }

    // Getters/ setters
    public Long getId() {return this.user_id1;}
    public void setId(Long user_id1) {this.user_id1 = user_id1;}

    public Long getUserId(){return this.user_id2;}
    public void setUserId(Long user_id2) {this.user_id2 = user_id2;}

}
