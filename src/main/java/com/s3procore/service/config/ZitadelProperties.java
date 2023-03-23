package com.s3procore.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "zitadel")
public class ZitadelProperties {

    private String globalToken;

    private String primaryDomain;

    private String createUserUrl;

    private String createUserMachineUrl;

    private String createSecretUserMachineUrl;

}
