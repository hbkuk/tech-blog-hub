package com.techbloghub.common.domain.base;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "MODIFIED_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}

