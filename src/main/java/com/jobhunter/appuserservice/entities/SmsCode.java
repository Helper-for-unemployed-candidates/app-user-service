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
@Table(indexes = @Index(columnList = "phone_number"))
public class SmsCode extends AbsUUIDNotUserAuditEntity {

    public SmsCode(String code, String phoneNumber) {
        this.code = code;
        this.phoneNumber = phoneNumber;
    }

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    private boolean checked = false;

    private boolean ignored = false;
}
