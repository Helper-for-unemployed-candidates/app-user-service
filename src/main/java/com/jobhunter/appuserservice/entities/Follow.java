package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Follow extends AbsUUIDEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Applicant applicant;
    private LocalDateTime followedAt;

}
