package com.s3procore.model.tenant;

import com.s3procore.model.Document;
import com.s3procore.model.application.Application;
import com.s3procore.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String domainName;

    @Enumerated(EnumType.STRING)
    private LocationTenant location;

    @Enumerated(EnumType.STRING)
    private EnvironmentTenant environment;


    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TenantUser> users = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    public void addUser(User user) {
        TenantUser tenantUser = new TenantUser(this, user);
        users.add(tenantUser);
        user.getTenants().add(tenantUser);
    }

    public void removeUser(User user) {
        for (Iterator<TenantUser> iterator = users.iterator();
             iterator.hasNext(); ) {
            TenantUser tenantUser = iterator.next();

            if (tenantUser.getTenant().equals(this) &&
                    tenantUser.getUser().equals(user)) {
                iterator.remove();
                tenantUser.getUser().getTenants().remove(tenantUser);
                tenantUser.setUser(null);
                tenantUser.setTenant(null);
            }
        }
    }
}
