package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByEmailIgnoreCaseOrPhoneIgnoreCase(String email, String phone);

    Optional<User> findFirstByEmailIgnoreCaseOrPhoneIgnoreCaseAndEnabledFalse(String email, String phone);

    boolean existsByEmailIgnoreCaseAndEnabledTrueAndIdNot(String email, UUID id);

    boolean existsByPhoneIgnoreCaseAndEnabledTrueAndIdNot(String phone, UUID id);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhoneIgnoreCase(String phone);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User SET accountNonLocked = false WHERE id = :userId AND role <> 'ADMIN'")
    void markAsBlocked(UUID userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User SET accountNonLocked = true WHERE id = :userId AND role <> 'ADMIN'")
    void markAsUnblocked(UUID userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User SET deleted = true WHERE id = :userId AND role <> 'ADMIN'")
    void markAsDeleted(UUID userId);
}
