package com.s3procore.core.subdomain.annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Controller
@RequestMapping
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SubdomainController {
	/**
	 * You can have multiple subdomains handled by the same controller.
	 */
	String[] value();
}