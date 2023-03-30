package com.s3procore.core.subdomain.context;

import com.s3procore.model.tenant.Tenant;
import org.springframework.lang.Nullable;

/**
 * Thread-safe tenant provider
 *
 */
public class TenantContext {
	private static final ThreadLocal<TenantHolder> threadLocal = new ThreadLocal<>();

	private static class TenantHolder {
		private Tenant tenant;

		TenantHolder(Tenant tenant) {
			this.tenant = tenant;
		}

		public Tenant getTenant() {
			return tenant;
		}
	}

	/**
	 * Set the tenant for the current request
	 */
	public static void setTenant(Tenant tenant) {
		TenantHolder tenantHolder = new TenantHolder(tenant);
		threadLocal.set(tenantHolder);
	}

	/**
	 * Get tenant from current request or null
	 */
	@Nullable
	public static Tenant getTenant() {
		TenantHolder holder = threadLocal.get();

		if (holder != null) {
			return holder.getTenant();
		}

		return null;
	}

	/**
	 * Return if the tenant holder has been previously updated but without a valid
	 * tenant.
	 */
	public static boolean hasInvalidTenantContext() {
		if (threadLocal.get() != null) {
			return threadLocal.get().getTenant() == null;
		}

		return false;
	}
}
