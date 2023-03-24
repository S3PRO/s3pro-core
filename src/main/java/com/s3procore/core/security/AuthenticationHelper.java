package com.s3procore.core.security;

import com.s3procore.model.user.User;
import com.s3procore.model.user.UserDetail;
import com.s3procore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationHelper {

    private final UserRepository userRepository;

    public UserDetail getAuthenticationDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String pid = authentication.getName();

        User user = userRepository.findByZitadelUserId(pid)
                .orElseThrow(() -> new IllegalStateException("Couldn't get user details due to user is not authenticated or not exist"));

        return UserDetail.builder()
                .id(user.getId())
                .companyId(user.getCompany().getId())
                .build();
    }

}
