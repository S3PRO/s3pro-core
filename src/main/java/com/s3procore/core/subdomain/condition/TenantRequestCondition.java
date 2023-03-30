package com.s3procore.core.subdomain.condition;

import com.s3procore.model.tenant.Tenant;
import com.s3procore.repository.TenantRepository;
import com.s3procore.core.subdomain.context.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link TenantRequestCondition} checks if the current requested subdomain
 * is known in our repository or cache and if yes, the controller having
 * {@link com.s3procore.core.subdomain.annotation.TenantController} annotated is used.
 * 
 */
public class TenantRequestCondition implements RequestCondition<TenantRequestCondition> {

	private final TenantRepository tenantRepository;

	/**
	 * A simple Map as a cache mapping known subdomains to its tenant. You would use
	 * something like Redis
	 * instead of a {@link ConcurrentHashMap}.
	 */
	private final ConcurrentHashMap<String, Tenant> knownTenants = new ConcurrentHashMap<>();

	/**
	 * Cache invalid requests. Again, for a larger system something like Redis would
	 * be more appropriate.
	 * Please also note that each controller has an instance of
	 * {@link TenantRequestCondition} assigned to it, so that this {@link HashSet}
	 * can have the size of ivnalidRequestedSubdomains*numberOfControllers.
	 */
	private final Set<String> invalidTenantRequests = new HashSet<>();

	public TenantRequestCondition(TenantRepository tenantRepository) {
		this.tenantRepository = tenantRepository;
	}

	@Override
	public TenantRequestCondition getMatchingCondition(HttpServletRequest request) {
		String subdomain = SubdomainUtil.extractSubdomain(request);
		TenantRequestCondition r = null;

		if (subdomain != null) {
			// subdomain request has been already cached as invalid
			if (!invalidTenantRequests.contains(subdomain)) {
				if (isTenantResolveable(subdomain)) {
					r = this;
				}
			}
		}

		return r;
	}

	/**
	 * Check if the given subdomain is
	 * <ul>
	 * <li>either already registered</li>
	 * <li>or can be loaded from the database</li> </ul
	 */
	boolean isTenantResolveable(String subdomain) {
		// check if the tenant has been already resolved
		Tenant resolved = knownTenants.getOrDefault(subdomain, null);

		// try to load the tenant
		if (resolved == null) {
			Optional<Tenant> tenant = tenantRepository.findByDomainName(subdomain);

			if (tenant.isPresent()) {
				knownTenants.put(tenant.get().getDomainName(), tenant.get());
				resolved = tenant.get();
			}
		}

		// update current TenantContext with tenant. This can be null.
		TenantContext.setTenant(resolved);

		return resolved != null;
	}


	@Override
	public TenantRequestCondition combine(TenantRequestCondition other) {
		// not relevant at the moment
		return null;
	}

	@Override
	public int compareTo(TenantRequestCondition other, HttpServletRequest request) {
		// not relevant at the moment
		return 0;
	}
}
