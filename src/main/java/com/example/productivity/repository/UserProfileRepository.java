package com.example.productivity.repository;

import com.example.productivity.model.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    UserProfile findByUserId(Long userId);
}