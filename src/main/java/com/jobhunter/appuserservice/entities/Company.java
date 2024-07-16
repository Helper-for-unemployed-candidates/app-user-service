package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company extends AbsUUIDEntity {
    private String companyName;
    private String companySphere;
    private String aboutCompany;
    private String companyLicense;
    private String officialWebsite;
    @OneToOne
    private User user;


}
