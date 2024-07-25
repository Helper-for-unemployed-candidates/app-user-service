package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailCodeRepository extends JpaRepository<EmailCode, UUID> {
    @Query(value = """
            SELECT :limitEmail <= (
                SELECT COUNT(*)
                FROM sms_code
                WHERE email = :email
                    AND created_at > (SELECT CURRENT_TIMESTAMP() - (INTERVAL '1' HOUR) * :limitHour)
                    AND ignored = false
            )""", nativeQuery = true)
    boolean nonOverLimit(@Param("limitEmail") int limitEmail, @Param("limitHour") int limitHour, @Param("email") String email);

    Optional<EmailCode> findFirstByEmailOrderByCreatedAtDesc(String email);
}
