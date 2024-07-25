package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(columnList = "user_id"))
public class Company extends AbsUUIDEntity {

    @Column(nullable = false)
    private String companyName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sphere companySphere;

    private String aboutCompany;

    private String companyLicense;

    private String officialWebsite;

    @OneToOne
    @JoinColumn(nullable = false, unique = true, name = "user_id")
    private User user;
}
