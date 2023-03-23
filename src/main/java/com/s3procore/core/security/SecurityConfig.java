package com.s3procore.core.security;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .requestMatchers(POST, "/api/users").permitAll()
                .requestMatchers(POST, "/api/user-machines").permitAll()
                .requestMatchers(POST, "/api/user-machines/generate-token").permitAll()
                .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .cors()
                .and()
                    .csrf()
                    .disable()
                .oauth2ResourceServer()
                .jwt();

        return httpSecurity.build();
    }

/*    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();

        return new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt jwt) {

                Collection<GrantedAuthority> grantedAuthorities = delegate.convert(jwt);

                if (jwt.getClaim("realm_access") == null) {
                    return grantedAuthorities;
                }
                JSONObject realmAccess = jwt.getClaim("realm_access");

                if (realmAccess.get("roles") == null) {
                    return grantedAuthorities;
                }
                JSONArray roles = (JSONArray) realmAccess.get("roles");

                final List<SimpleGrantedAuthority> keycloakAuthorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();

                grantedAuthorities.addAll(keycloakAuthorities);
                return grantedAuthorities;
            }
        };
    }*/

}
