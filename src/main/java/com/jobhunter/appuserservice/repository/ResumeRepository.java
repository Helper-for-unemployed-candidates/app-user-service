package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {
}
