package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import com.jobhunter.appuserservice.enums.TypeOfEducation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Education extends AbsUUIDEntity {

    private TypeOfEducation type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @Column(nullable = false, columnDefinition = "timestamp(6) CHECK (from_date < now())")
    private Date fromDate;

    private Date toDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Resume resume;

}
