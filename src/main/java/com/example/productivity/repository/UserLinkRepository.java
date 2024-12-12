package com.example.productivity.repository;

import com.example.productivity.model.UserLink;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserLinkRepository extends CrudRepository<UserLink,Long> {
    List<UserLink> findByUserId(Long id);

}
