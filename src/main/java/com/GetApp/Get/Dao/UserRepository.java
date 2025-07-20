package com.GetApp.Get.Dao;

import com.GetApp.Get.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findByIsActiveTrue();

    List<User> findByIsVerifiedTrue();

    @Query("SELECT u FROM User u WHERE u.city = :city AND u.state = :state AND u.isActive = true")
    List<User> findByLocation(@Param("city") String city, @Param("state") String state);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:name% OR u.lastName LIKE %:name%")
    List<User> findByNameContaining(@Param("name") String name);

    Page<User> findByIsActiveTrue(Pageable pageable);


    User getUserByEmail(String email);
}
