package com.example.javawebapplication.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@javax.persistence.EntityListeners(AuditingEntityListener.class)
@javax.persistence.MappedSuperclass
public abstract class AuditingFields {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    // TODO: Add createdBy, modifiedBy

}
