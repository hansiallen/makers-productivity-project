package com.example.productivity.repository;

import com.example.productivity.model.UserProfiles;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfiles, Long> {
}