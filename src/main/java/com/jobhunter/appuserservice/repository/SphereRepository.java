package com.jobhunter.appuserservice.repository;

import com.jobhunter.appuserservice.entities.Sphere;
import com.jobhunter.appuserservice.repository.projection.SphereProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SphereRepository extends JpaRepository<Sphere, UUID> {
    @Query(nativeQuery = true, value = "SELECT id, name FROM sphere")
    List<SphereProjection> list();
}
