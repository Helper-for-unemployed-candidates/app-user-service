package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Applicant extends AbsUUIDEntity {
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;

    @OneToOne
    private User user;

}
