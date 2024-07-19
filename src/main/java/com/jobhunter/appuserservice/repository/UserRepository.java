package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByEmailIgnoreCaseOrPhoneIgnoreCase(String email, String phone);

    Optional<User> findFirstByEmailIgnoreCaseOrPhoneIgnoreCaseAndEnabledFalse(String email, String phone);

    boolean existsByEmailIgnoreCaseAndEnabledTrue(String email);

    boolean existsByPhoneIgnoreCaseAndEnabledTrue(String phone);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhoneIgnoreCase(String phone);
}
