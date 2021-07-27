package com.person.api.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class SimpleEntity {

    @Column(name = "DT_INCLUSION", nullable = false)
    private LocalDateTime inclusionDate;

    @Column(name = "UUID", unique = true, nullable = false)
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getInclusionDate() {
        return inclusionDate;
    }

    public void setInclusionDate(LocalDateTime inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    @PrePersist
    public void setupDefaultProperties() {
        this.inclusionDate = LocalDateTime.now();
        if (Objects.isNull(this.uuid)) {
            this.uuid = UUID.randomUUID().toString();
        }


    }
}
