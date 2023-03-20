package com.s3procore.repository;

import com.s3procore.model.Tenancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenancyRepository extends JpaRepository<Tenancy, Long> {
}
