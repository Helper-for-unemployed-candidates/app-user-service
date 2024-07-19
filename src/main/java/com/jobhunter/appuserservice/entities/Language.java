package com.jobhunter.appuserservice.entities;

import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import com.jobhunter.appuserservice.enums.Languages;
import com.jobhunter.appuserservice.enums.Level;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Language extends AbsUUIDEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Languages languages;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Resume resume;
}
