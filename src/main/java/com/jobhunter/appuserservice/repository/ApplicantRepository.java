package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.Applicant;
import com.jobhunter.appuserservice.repository.projection.ApplicantProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    Optional<Applicant> findByUserId(UUID userId);

    @Query(nativeQuery = true, value = """
            SELECT a.first_name             AS firstName,
                   a.middle_name            AS middleName,
                   a.last_name              AS lastName,
                   u.email                  AS email,
                   u.phone                  AS phoneNumber,
                   a.birth_date             AS birthDate,
                   a.gender                 AS gender,
                   NOT u.account_non_locked AS blocked,
                   u.enabled                AS enabled
            FROM applicant a
                     INNER JOIN users u ON a.user_id = u.id
            WHERE u.role <> 'ADMIN'
              AND u.deleted = false
              AND a.deleted = false""")
    Page<ApplicantProjection> list(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Applicant SET deleted = true WHERE user = :userId")
    void markAsDeleted(UUID userId);
}
