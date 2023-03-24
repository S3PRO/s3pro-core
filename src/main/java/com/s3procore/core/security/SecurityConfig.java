package com.s3procore.core.security;

import com.google.common.collect.ObjectArrays;
import com.s3procore.core.security.util.DefaultProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationContext applicationContext;

    private static final String[] SWAGGER_PATHS = {
            // -- swagger ui
            "/v3/api-docs/swagger-config",
            "/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/webjars/**"
    };

    private static final String[] MONITORING_PATHS = {
            "/actuator/**"
    };

    private final String SIGNUP_USER_PATH = "/api/users";
    private final String SIGNUP_USER_MACHINE_PATH = "/api/user-machines";
    private final String GENERATE_TOKEN_USER_MACHINE_PATH = "/api/user-machines";


    @Bean
    public SecurityFilterChain securityChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(getPublicRoutes()).permitAll()
                .requestMatchers(MONITORING_PATHS).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        return httpSecurity.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        return new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {
                Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("urn:zitadel:iam:org:project:roles");

                if (realmAccess == null) {
                    return new ArrayList<>();
                }

                return realmAccess.keySet().stream().map(roleName -> "ROLE_" + roleName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);

        final List<String> wildcards = Collections.singletonList("*");
        configuration.setAllowedHeaders(wildcards);
        configuration.setAllowedMethods(wildcards);
        configuration.setAllowedOrigins(wildcards);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    /**
     * Specify that URLs are allowed by anyone.
     * Swagger routes allowed only on dev and stage or default profile
     */
    private String[] getPublicRoutes() {
        final Environment env = applicationContext.getEnvironment();
        final boolean isProd = DefaultProfileUtil.isProdProfile(env);

        final String[] authPaths = {SIGNUP_USER_PATH, SIGNUP_USER_MACHINE_PATH, GENERATE_TOKEN_USER_MACHINE_PATH};

        if (isProd) {
            return authPaths;
        } else {
            return ObjectArrays.concat(SWAGGER_PATHS, authPaths, String.class);
        }

    }
}
