package com.jobhunter.appuserservice.entities;

import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address extends AbsUUIDEntity {
    private String city;
    private String region;
    private String fullAddress;
}
