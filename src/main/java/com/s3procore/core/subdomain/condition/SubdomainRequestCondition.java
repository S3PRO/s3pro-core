package com.s3procore.core.subdomain.condition;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.Set;

/**
 * {@link SubdomainRequestCondition} checks if the current subdomain matches one
 * of the controller's attached value inside the {@link com.s3procore.core.subdomain.annotation.SubdomainController}
 * annotation.
 *
 */
public class SubdomainRequestCondition implements RequestCondition<SubdomainRequestCondition> {

	/**
	 * This set contains all subdomains which are mapped for the given controller,
	 * which has the instance of this {@link SubdomainRequestCondition} attached.
	 */
	private final Set<String> allowedSubdomains;

	public SubdomainRequestCondition(Set<String> allowedSubdomains) {
		this.allowedSubdomains = allowedSubdomains;
	}

	@Override
	public SubdomainRequestCondition getMatchingCondition(HttpServletRequest request) {
		SubdomainRequestCondition r = null;

		String useSubdomain = SubdomainUtil.extractSubdomain(request);

		if (!StringUtils.hasText(useSubdomain)) {
			if (allowedSubdomains.contains(useSubdomain)) {
				r = this;
			}
		}

		return r;
	}

	@Override
	public int compareTo(SubdomainRequestCondition other, HttpServletRequest request) {
		return 0;
	}

	@Override
	public SubdomainRequestCondition combine(SubdomainRequestCondition other) {
		// not relevant at the moment
		return null;
	}

}