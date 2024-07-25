package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCode, UUID> {
    @Query(value = """
            SELECT :limitSms <= (
                SELECT COUNT(*)
                FROM sms_code
                WHERE phone_number = :phoneNumber
                    AND created_at > (SELECT CURRENT_TIMESTAMP() - (INTERVAL '1' HOUR) * :limitHour)
                    AND ignored = false
            )""", nativeQuery = true)
    boolean nonOverLimit(@Param("limitSms") int limitSms, @Param("limitHour") int limitHour, @Param("phoneNumber") String phoneNumber);

    Optional<SmsCode> findFirstByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);
}
