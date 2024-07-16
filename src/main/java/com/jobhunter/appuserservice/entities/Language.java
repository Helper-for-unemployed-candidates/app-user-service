package com.jobhunter.appuserservice.entities;

import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Language extends AbsUUIDEntity {
    private Languages name;
    private Level level;
    @ManyToOne
    private Resume resume;
}
