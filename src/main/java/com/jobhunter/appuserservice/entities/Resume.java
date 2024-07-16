package com.jobhunter.appuserservice.entities;

import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
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
    private User user;
    private String about;
    @OneToMany(mappedBy = "resume")
    private List<Experience> experience;
    @OneToMany(mappedBy = "resume")
    private List<Education> education;
    @OneToMany(mappedBy = "resume")
    private List<Skill> skills;
    @OneToMany
    private List<Language> language;




    /*

Resume	User	user	String	about	mappedBy	experiences	mappedBy	education	List	skills	mappedBy	Language	Key	Field
    * */
}
