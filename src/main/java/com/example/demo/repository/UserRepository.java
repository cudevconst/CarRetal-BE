package com.example.demo.repository;


import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "", nativeQuery = true)
    User findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM user u where u.username = ?1", nativeQuery = true)

    Optional<User> findByUsername(String username);

    @Query(value = "select * from user where role like ('user')", nativeQuery = true)
    List<User> findAllUser();
}
