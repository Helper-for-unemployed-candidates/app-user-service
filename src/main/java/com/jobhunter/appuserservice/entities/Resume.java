package com.jobhunter.appuserservice.entities;

import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Resume extends AbsUUIDEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private String about;

    @OneToMany(mappedBy = "resume")
    private List<Experience> experience;

    @OneToMany(mappedBy = "resume")
    private List<Education> education;

    @OneToMany
    private List<Skill> skills;

    @OneToMany(mappedBy = "resume")
    private List<Language> language;
}
