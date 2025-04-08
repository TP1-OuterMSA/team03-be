package com.example.SchoolLunchReport.global.common;

import com.example.SchoolLunchReport.global.common.BaseTimeEntity.CreatedAtListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@MappedSuperclass
@EntityListeners(CreatedAtListener.class)
public class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDate createdAt;

    private void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public static class CreatedAtListener {

        @PrePersist
        public void setCreatedAt(BaseTimeEntity entity) {
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(LocalDate.now());
            }
        }
    }
}
