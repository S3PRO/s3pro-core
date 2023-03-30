package com.s3procore.core.subdomain;

import com.s3procore.core.subdomain.mapper.SubdomainRequestMappingHandlerMapping;
import com.s3procore.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@RequiredArgsConstructor
public class CustomWebMvcRegistrations implements WebMvcRegistrations {

	private final TenantRepository tenantRepository;

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new SubdomainRequestMappingHandlerMapping(tenantRepository);
	}

}
