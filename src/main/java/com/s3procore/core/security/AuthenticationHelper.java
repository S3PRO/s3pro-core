package com.s3procore.core.security;

import com.s3procore.dto.security.AuthenticationDetailsDto;
import com.s3procore.model.tenant.Tenant;
import com.s3procore.model.user.User;
import com.s3procore.model.user.UserDetail;
import com.s3procore.repository.TenantRepository;
import com.s3procore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationHelper {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    public AuthenticationDetailsDto getAuthenticationDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pid = authentication.getName();

        User user = userRepository.findBySubId(pid)
                .orElseThrow(() -> new IllegalStateException("Couldn't get user details due to user is not authenticated or not exist"));

        return AuthenticationDetailsDto.builder()
                .userId(user.getId())
                .login(user.getEmail())
                .build();
    }

    public AuthenticationDetailsDto getAuthenticationDetailsByDomainName(String domainName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pid = authentication.getName();

        Tenant tenant = tenantRepository.findByDomainNameAndUserSub(domainName, pid)
                .orElseThrow(() -> new IllegalStateException("Couldn't get tenant details due to user is not authenticated or not exist"));

        User user = userRepository.findBySubId(pid)
                .orElseThrow(() -> new IllegalStateException("Couldn't get user details due to user is not authenticated or not exist"));

        return AuthenticationDetailsDto.builder()
                .userId(user.getId())
                .login(user.getEmail())
                .tenantId(tenant.getId())
                .build();
    }

}
