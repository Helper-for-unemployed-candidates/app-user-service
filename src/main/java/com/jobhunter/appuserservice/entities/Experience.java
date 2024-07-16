package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Experience extends AbsUUIDEntity {
    private String companyName;
    private Date from;
    private Date to;
    private String position;
    private String description;
    @ManyToOne
    private Resume resume;
}
