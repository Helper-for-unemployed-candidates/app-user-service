package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Specialization extends AbsUUIDEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Faculty faculty;
}
