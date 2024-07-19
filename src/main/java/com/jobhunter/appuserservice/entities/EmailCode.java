package com.jobhunter.appuserservice.entities;

import com.jobhunter.appuserservice.entities.template.AbsUUIDNotUserAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(indexes = @Index(columnList = "email"))
public class EmailCode extends AbsUUIDNotUserAuditEntity {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String email;

    private boolean checked = false;

    private boolean ignored = false;
}
