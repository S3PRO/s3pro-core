package com.s3procore.model.tenant;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@Setter
@FilterDef(name = AbstractTenantable.TENANT_FILTER, parameters = {
        @ParamDef(name = AbstractTenantable.COMPANY_ID, type = Long.class)
})
@Filter(name = "tenantFilter", condition =
        "(:companyId = -1 or company_id = :companyId) ")
@MappedSuperclass
public abstract class AbstractTenantable implements Tenantable {

    private Long companyId;

    public static final String TENANT_FILTER = "tenantFilter";
    public static final String COMPANY_ID = "companyId";


    public void populateTenant(Tenantable tenant) {
        this.companyId = tenant.getCompanyId();
    }
}
