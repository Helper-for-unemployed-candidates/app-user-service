package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Experience extends AbsUUIDEntity {
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, columnDefinition = "timestamp(6) CHECK (from_date < now())")
    private Date fromDate;

    private Date toDate;

    @Column(nullable = false)
    private String position;

    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Resume resume;
}
