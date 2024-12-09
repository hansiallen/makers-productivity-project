package com.example.productivity.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Boolean.TRUE;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "auth0_id")
    private String auth0Id;
    private String email;
    public User(String auth0Id, String email) {
        this.auth0Id = auth0Id;
        this.email = email;
    }

    public User() {
    }

    public Long getId() {return this.id;}
    public void setId(Long id) {this.id = id;}
    public String getAuth0Id() {return this.auth0Id;}
    public void setAuth0Id(String auth0Id) {this.auth0Id = auth0Id;}
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
}
