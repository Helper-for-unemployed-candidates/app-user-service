package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
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
    private Specialization specialization;
    private Date from;
    private Date to;
    @ManyToOne
    private Resume resume;

}
