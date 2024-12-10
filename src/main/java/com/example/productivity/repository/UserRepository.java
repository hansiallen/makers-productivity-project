package com.example.productivity.repository;

import com.example.productivity.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByAuth0Id(String auth0Id);
    
}
