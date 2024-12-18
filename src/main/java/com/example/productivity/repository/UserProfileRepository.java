package com.example.productivity.repository;
import com.example.productivity.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserId(Long userId);

    List<UserProfile> findByUserIdIn(List<Long> userIds);

    @Query(value = "SELECT up.* " +
            "FROM user_profiles up " +
            "JOIN contacts c ON up.user_id = c.user_id2 " +
            "WHERE c.user_id1 = :currentUserId " +
            "AND LOWER(CONCAT(up.first_name, ' ', up.last_name)) LIKE %:query%",
            nativeQuery = true)
//    @Query(value = "SELECT * FROM user_profiles WHERE LOWER(CONCAT(first_name, ' ', last_name)) LIKE %:query%", nativeQuery = true)
    List<UserProfile> searchByName(@Param("query") String query, @Param("currentUserId") Long currentUserId);

    @Query(value = "SELECT up.* " +
            "FROM user_profiles up " +
            "JOIN contacts c ON up.user_id = c.user_id2 " +
            "WHERE c.user_id1 = :currentUserId",
            nativeQuery = true)
    List<UserProfile> findContactsByUserId(@Param("currentUserId") Long currentUserId);
}