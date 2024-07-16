package com.jobhunter.appuserservice.entities;


import com.jobhunter.appuserservice.entities.template.AbsUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;


import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends AbsUUIDEntity {

    private String email;
    private String password;
    private UUID verificationCode;
    @OneToOne
    private Attachment avatar;
    @OneToOne
    private Address address;
    private Role role;

}
