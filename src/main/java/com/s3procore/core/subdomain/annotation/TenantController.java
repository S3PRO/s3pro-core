package com.s3procore.core.subdomain.annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker interface to indicate that the mapped controller belongs to some
 * tenant. The belonging to the tenant is resolved by checking the subdomain of
 * the request, e.g. "my-tenant.app.com" would belong to the tenant "my-tenant".
 */
@Controller
@RequestMapping
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantController {
}