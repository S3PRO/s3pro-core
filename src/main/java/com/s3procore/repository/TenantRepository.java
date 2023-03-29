package com.s3procore.repository;

import com.s3procore.model.tenant.LocationTenant;
import com.s3procore.model.tenant.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsByDomainNameAndLocation(String domainName, LocationTenant location);

    boolean existsByDomainNameAndLocationAndIdNot(String domainName, LocationTenant location, long id);

    Optional<Tenant> findByDomainName(String domainName);

    @Query("select t from Tenant t join t.users u where t.domainName = :domainName and u.subId = :subId")
    Optional<Tenant> findByDomainNameAndUserSub(@Param("domainName") String domainName,
                                                @Param("subId") String subId);

    @Query("select t from Tenant t join t.users u where u.id = :userId order by t.id")
    List<Tenant> findAllByUserId(@Param("userId") Long userId);


}
