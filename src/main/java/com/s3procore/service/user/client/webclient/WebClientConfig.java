package com.s3procore.service.user.client.webclient;

import com.s3procore.service.user.client.config.ZitadelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final ZitadelProperties zitadelProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(zitadelProperties.getPrimaryDomain())
                .build();
    }
}
