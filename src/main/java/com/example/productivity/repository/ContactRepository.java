package com.example.productivity.repository;

import com.example.productivity.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact,Long> {

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS has_contact FROM Contact WHERE (user_id1 = :user_id1 AND user_id2 = :user_id2) OR (user_id1 = :user_id2 AND user_id2 = :user_id1)")
    boolean usersInContacts(@Param("user_id1") Long userId1, @Param("user_id2") Long user_id2);

    @Query(value = "SELECT user_id2 FROM contacts WHERE user_id1 = :userId AND is_favourite = true", nativeQuery = true)
    List<Long> findFavouritesUserIdsByUser1Id(Long userId);

    @Query(value = "SELECT user_id2 FROM contacts WHERE user_id1 = :userId", nativeQuery = true)
    List<Long> findUserIdsByUser1Id(Long userId);

    @Query(value = "SELECT * FROM contacts WHERE user_id1 = :userId1 AND user_id2 = :userId2", nativeQuery = true)
    Contact findContactByUserId1AndUserId2(Long userId1, Long userId2);

    @Query(value = "DELETE FROM contacts WHERE user_id1 = :userId1 AND user_id2 = :userId2", nativeQuery = true)
    void deleteByUserId1AndUserId2(Long userId1, Long userId2);

    @Query(value = "SELECT COALESCE(is_favourite, false) FROM contacts WHERE user_id1 = :userId1 AND user_id2 = :userId2", nativeQuery = true)
    Boolean isContactFavourite(Long userId1, Long userId2);
}
