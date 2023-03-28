package com.s3procore.service.user.client;

import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.SecretApplicationBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.ApplicationBeanDto;
import com.s3procore.dto.user.bean.usermachine.ApplicationBeanResponseDto;
import com.s3procore.service.user.client.config.ZitadelProperties;
import com.s3procore.service.exception.GenericException;
import com.s3procore.service.exception.ValidationException;
import com.s3procore.service.user.converter.CreateUpdateUserDtoToUserBeanDtoConverter;
import com.s3procore.service.application.converter.CreateApplicationDtoToApplicationBeanDtoConverter;
import com.s3procore.service.validation.ValidationCode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserClientImpl implements UserClient {

    private final RestTemplate restTemplate;
    private final ZitadelProperties zitadelProperties;
    private final CreateUpdateUserDtoToUserBeanDtoConverter createUpdateUserDtoToUserBeanDtoConverter;
    private final CreateApplicationDtoToApplicationBeanDtoConverter createApplicationDtoToApplicationBeanDtoConverter;

    public UserClientImpl(RestTemplateBuilder restTemplateBuilder,
                          ZitadelProperties zitadelProperties,
                          CreateUpdateUserDtoToUserBeanDtoConverter createUpdateUserDtoToUserBeanDtoConverter,
                          CreateApplicationDtoToApplicationBeanDtoConverter createApplicationDtoToApplicationBeanDtoConverter) {
        this.restTemplate = restTemplateBuilder.build();
        this.zitadelProperties = zitadelProperties;
        this.createUpdateUserDtoToUserBeanDtoConverter = createUpdateUserDtoToUserBeanDtoConverter;
        this.createApplicationDtoToApplicationBeanDtoConverter = createApplicationDtoToApplicationBeanDtoConverter;
    }

    @Override
    public UserBeanResponseDto createUser(CreateUpdateUserDto createUpdateUserDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", zitadelProperties.getGlobalToken());

        HttpEntity<UserBeanDto> request = new HttpEntity<>(createUpdateUserDtoToUserBeanDtoConverter.convert(createUpdateUserDto), headers);

        String url = String.join("", zitadelProperties.getPrimaryDomain(), zitadelProperties.getCreateUserUrl());

        try {
            ResponseEntity<UserBeanResponseDto> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST, request, UserBeanResponseDto.class);

            UserBeanResponseDto userBeanResponseDto = responseEntity.getBody();

            if (userBeanResponseDto.getUserId() == null) {
                throw new ValidationException("ZITADEL_USER_ID_ERROR");
            }

            return userBeanResponseDto;
        } catch (Exception e) {
            throw new GenericException(ValidationCode.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public ApplicationBeanResponseDto createApplication(CreateApplicationDto createApplicationDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", zitadelProperties.getGlobalToken());

        HttpEntity<ApplicationBeanDto> request = new HttpEntity<>(createApplicationDtoToApplicationBeanDtoConverter.convert(createApplicationDto), headers);

        String url = String.join("", zitadelProperties.getPrimaryDomain(), zitadelProperties.getCreateUserMachineUrl());

        try {
            ResponseEntity<ApplicationBeanResponseDto> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST, request, ApplicationBeanResponseDto.class);

            ApplicationBeanResponseDto applicationBeanResponseDto = responseEntity.getBody();

            if (applicationBeanResponseDto.getUserId() == null) {
                throw new ValidationException("ZITADEL_USER_ID_ERROR");
            }

            SecretApplicationBeanResponseDto secretApplicationBeanResponseDto = createApplicationSecretCredentials(applicationBeanResponseDto.getUserId());
            applicationBeanResponseDto.setClientId(secretApplicationBeanResponseDto.getClientId());
            applicationBeanResponseDto.setClientSecret(secretApplicationBeanResponseDto.getClientSecret());

            return applicationBeanResponseDto;
        } catch (Exception e) {
            throw new GenericException(ValidationCode.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public SecretApplicationBeanResponseDto createApplicationSecretCredentials(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", zitadelProperties.getGlobalToken());

        HttpEntity<ApplicationBeanDto> request = new HttpEntity<>(headers);

        String url = String.join("", zitadelProperties.getPrimaryDomain(), zitadelProperties.getCreateSecretUserMachineUrl(), userId, "/secret");

        try {
            ResponseEntity<SecretApplicationBeanResponseDto> responseEntity =
                    restTemplate.exchange(url, HttpMethod.PUT, request, SecretApplicationBeanResponseDto.class);

            SecretApplicationBeanResponseDto secretApplicationBeanResponseDto = responseEntity.getBody();

            if (secretApplicationBeanResponseDto.getClientId() == null) {
                throw new ValidationException("ZITADEL_CLIENT_ID_ERROR");
            }

            return secretApplicationBeanResponseDto;
        } catch (Exception e) {
            throw new GenericException(ValidationCode.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public ApplicationTokenDto getApplicationToken(String clientId, String clientSecret) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", zitadelProperties.getGlobalToken());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("scope", "openid profile email urn:zitadel:iam:org:project:id:zitadel:aud");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<ApplicationTokenDto> responseEntity =
                    restTemplate.exchange("https://s3pro-dev-01-pnmsfb.zitadel.cloud/oauth/v2/token", HttpMethod.POST, entity,
                            ApplicationTokenDto.class);

            ApplicationTokenDto applicationTokenDto = responseEntity.getBody();

            return applicationTokenDto;
        } catch (Exception e) {
            throw new GenericException(ValidationCode.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

}
