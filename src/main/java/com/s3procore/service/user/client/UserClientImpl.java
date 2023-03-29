package com.s3procore.service.user.client;

import com.s3procore.dto.application.CreateApplicationDto;
import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.ApplicationTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.SecretApplicationBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.ApplicationBeanResponseDto;
import com.s3procore.service.user.client.config.ZitadelProperties;
import com.s3procore.service.exception.GenericException;
import com.s3procore.service.user.converter.CreateUpdateUserDtoToUserBeanDtoConverter;
import com.s3procore.service.application.converter.CreateApplicationDtoToApplicationBeanDtoConverter;
import com.s3procore.service.validation.ValidationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

    private final ZitadelProperties zitadelProperties;
    private final WebClient webClient;
    private final CreateUpdateUserDtoToUserBeanDtoConverter createUpdateUserDtoToUserBeanDtoConverter;
    private final CreateApplicationDtoToApplicationBeanDtoConverter createApplicationDtoToApplicationBeanDtoConverter;

    @Override
    public UserBeanResponseDto createUser(CreateUpdateUserDto createUpdateUserDto) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(zitadelProperties.getCreateUserUrl()).build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(zitadelProperties.getGlobalToken()))
                .body(BodyInserters.fromValue(createUpdateUserDtoToUserBeanDtoConverter.convert(createUpdateUserDto)))
                .retrieve()
                .bodyToMono(UserBeanResponseDto.class)
                .block();
    }

    @Override
    public ApplicationBeanResponseDto createApplication(CreateApplicationDto createApplicationDto) {
        ApplicationBeanResponseDto applicationBeanResponseDto = webClient.post()
                .uri(uriBuilder -> uriBuilder.path(zitadelProperties.getCreateUserMachineUrl()).build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(zitadelProperties.getGlobalToken()))
                .body(BodyInserters.fromValue(createApplicationDtoToApplicationBeanDtoConverter.convert(createApplicationDto)))
                .retrieve()
                .bodyToMono(ApplicationBeanResponseDto.class)
                .block();

        SecretApplicationBeanResponseDto secretApplicationBeanResponseDto = createApplicationSecretCredentials(applicationBeanResponseDto.getUserId());
        applicationBeanResponseDto.setClientId(secretApplicationBeanResponseDto.getClientId());
        applicationBeanResponseDto.setClientSecret(secretApplicationBeanResponseDto.getClientSecret());

        return applicationBeanResponseDto;
    }

    @Override
    public SecretApplicationBeanResponseDto createApplicationSecretCredentials(String userId) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path(zitadelProperties.getCreateSecretUserMachineUrl() + userId + "/secret").build())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(zitadelProperties.getGlobalToken()))
                .retrieve()
                .bodyToMono(SecretApplicationBeanResponseDto.class)
                .block();
    }

    @Override
    public ApplicationTokenDto getApplicationToken(String clientId, String clientSecret) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + zitadelProperties.getGlobalToken());

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
