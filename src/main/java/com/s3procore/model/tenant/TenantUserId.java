package com.s3procore.model.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TenantUserId implements Serializable {

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "user_id")
    private Long userId;

    public TenantUserId() {
    }

    public TenantUserId(Long tenantId, Long userId) {
        this.tenantId = tenantId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TenantUserId that = (TenantUserId) o;
        return Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, userId);
    }
}
