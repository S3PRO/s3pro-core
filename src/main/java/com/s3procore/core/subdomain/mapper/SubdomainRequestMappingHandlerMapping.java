package com.s3procore.core.subdomain.mapper;

import com.s3procore.repository.TenantRepository;
import com.s3procore.core.subdomain.annotation.SubdomainController;
import com.s3procore.core.subdomain.annotation.TenantController;
import com.s3procore.core.subdomain.condition.SubdomainRequestCondition;
import com.s3procore.core.subdomain.condition.TenantRequestCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.HashSet;

public class SubdomainRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	private final TenantRepository tenantRepository;

	public SubdomainRequestMappingHandlerMapping(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}

	@Override
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		// if the given class is annotated with SubdomainController, we prefer it
		SubdomainController subdomainController;

		if ((subdomainController = AnnotationUtils.findAnnotation(handlerType, SubdomainController.class)) != null) {
			// we need to extract the mapped subdomains as we don't have access to this
			// information during runtime
			return new SubdomainRequestCondition(new HashSet<>(Arrays.asList(subdomainController.value())));
		}

		// is TenantController annotation present?
		if (AnnotationUtils.findAnnotation(handlerType, TenantController.class) != null) {
			return new TenantRequestCondition(tenantRepository);
		}

		// no SubdomainController or TenantController annotation? Then don't use any
		// conditions and fallback to the default handling without any condiitions.
		return null;
	}
}