package com.s3procore.model.tenant;

import com.s3procore.model.user.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tenant_user")
public class TenantUser {

    @EmbeddedId
    private TenantUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tenantId")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    public TenantUser() {
    }

    public TenantUser(Tenant tenant, User user) {
        this.tenant = tenant;
        this.user = user;
        this.id = new TenantUserId(tenant.getId(), user.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TenantUser that = (TenantUser) o;
        return Objects.equals(tenant, that.tenant) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenant, user);
    }
}
