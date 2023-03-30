package com.s3procore.core.subdomain.condition;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;


public final class SubdomainUtil {

	private SubdomainUtil() {
	}

	/**
	 * Extract the subdomain from the server name or null.
	 */
	@Nullable
	public static String extractSubdomain(HttpServletRequest req) {
		int idx = -1;

		if ((idx = req.getServerName().indexOf(".")) >= 0) {
			return req.getServerName().substring(0, idx);
		}

		return null;
	}
}
