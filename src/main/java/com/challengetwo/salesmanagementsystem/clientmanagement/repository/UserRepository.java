package com.challengetwo.salesmanagementsystem.clientmanagement.repository;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.Role;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.Status;
import com.challengetwo.salesmanagementsystem.clientmanagement.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query("UPDATE User user SET user.status = ?1 WHERE user.email = ?2")
    void enableUser(Status verified, String email);

    Optional<User> findByRole(Role role);
}
