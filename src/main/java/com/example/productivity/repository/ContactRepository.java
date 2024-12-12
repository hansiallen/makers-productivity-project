package com.example.productivity.repository;

import com.example.productivity.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact,Long> {
    @Query(value = "SELECT user_id2 FROM contacts WHERE user_id1 = :userId AND is_favourite = true", nativeQuery = true)
    List<Long> findFavouritesUserIdsByUser1Id(Long userId);

    @Query(value = "SELECT * FROM contacts WHERE user_id1 = :userId1 AND user_id2 = :userId2", nativeQuery = true)
    Contact findContactByUserId1AndUserId2(Long userId1, Long userId2);
}
