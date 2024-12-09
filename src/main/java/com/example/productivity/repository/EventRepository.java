package com.example.productivity.repository;

import com.example.productivity.model.User;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<User, Long> {
}
