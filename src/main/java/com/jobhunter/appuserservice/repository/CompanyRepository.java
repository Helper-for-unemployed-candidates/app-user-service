package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.Company;
import com.jobhunter.appuserservice.repository.projection.CompanyProjection;
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
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByUserId(UUID userId);

    @Query(nativeQuery = true, value = """
            SELECT c.company_name           AS companyName,
                   s.name                   AS companySphere,
                   c.about_company          AS aboutCompany,
                   c.company_license        AS companyLisence,
                   c.official_website       AS officialWebsite,
                   u.email                  AS email,
                   u.phone                  AS phoneNumber,
                   NOT u.account_non_locked AS blocked,
                   u.enabled                AS enabled
            FROM company c
                     INNER JOIN users u ON c.user_id = u.id
                     LEFT JOIN sphere s ON c.company_sphere_id = s.id
            WHERE u.role <> 'ADMIN'
              AND u.deleted = false
              AND c.deleted = false""")
    Page<CompanyProjection> list(Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "UPDATE Company SET deleted = true WHERE user = :userId")
    void markAsDeleted(UUID userId);
}
