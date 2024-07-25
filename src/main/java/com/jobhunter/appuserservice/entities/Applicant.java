package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import com.jobhunter.appuserservice.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(columnList = "user_id"))
public class Applicant extends AbsUUIDEntity {
    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date birthDate;

    @OneToOne
    @JoinColumn(nullable = false, unique = true, name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
