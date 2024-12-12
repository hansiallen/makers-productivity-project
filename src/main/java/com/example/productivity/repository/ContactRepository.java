package com.example.productivity.repository;

import com.example.productivity.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ContactRepository extends CrudRepository<Contact,Long> {

    @Query("SELECT COUNT(*) FROM Contact WHERE (user_id1 = :user_id1 AND user_id2 = :user_id2) OR (user_id1 = :user_id2 AND user_id2 = :user_id1)")
    Integer usersInContacts(@Param("user_id1") Long userId1, @Param("user_id2") Long user_id2); // returns either 0 (not in contact) or 1 (in contact)

    @Query(value = "SELECT user_id2 FROM contacts WHERE user_id1 = :userId AND is_favourite = true", nativeQuery = true)
    List<Long> findFavouritesUserIdsByUser1Id(Long userId);

}
