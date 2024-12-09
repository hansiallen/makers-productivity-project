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
    private Long user_id;

    // Constructors
    public Contact(){}
    public Contact(Long id, Long user_id){
        this.id = id;
        this.user_id = user_id;
    }

    // Getters/ setters
    public Long getId() {return this.id;}
    public void setId(Long id) {this.id = id;}

    public Long getUserId(){return this.user_id;}
    public void setUserId(Long user_id) {this.user_id = user_id;}

}
