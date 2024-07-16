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
public class Faculty extends AbsUUIDEntity {
    private String name;
}
