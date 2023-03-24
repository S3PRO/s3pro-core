package com.s3procore.service.user.client;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.CreateUpdateUserMachineDto;
import com.s3procore.dto.user.UserMachineTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.SecretUserMachineBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.UserBeanMachineDto;
import com.s3procore.dto.user.bean.usermachine.UserMachineBeanResponseDto;
import com.s3procore.service.config.ZitadelProperties;
import com.s3procore.service.constant.ErrorCodes;
import com.s3procore.service.exception.GenericException;
import com.s3procore.service.exception.ValidationException;
import com.s3procore.service.user.converters.CreateUpdateUserDtoToUserBeanDtoConverter;
import com.s3procore.service.user.converters.CreateUpdateUserMachineDtoToUserMachineBeanDtoConverter;
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
    private final CreateUpdateUserMachineDtoToUserMachineBeanDtoConverter createUpdateUserMachineDtoToUserMachineBeanDtoConverter;

    public UserClientImpl(RestTemplateBuilder restTemplateBuilder,
                          ZitadelProperties zitadelProperties,
                          CreateUpdateUserDtoToUserBeanDtoConverter createUpdateUserDtoToUserBeanDtoConverter,
                          CreateUpdateUserMachineDtoToUserMachineBeanDtoConverter createUpdateUserMachineDtoToUserMachineBeanDtoConverter) {
        this.restTemplate = restTemplateBuilder.build();
        this.zitadelProperties = zitadelProperties;
        this.createUpdateUserDtoToUserBeanDtoConverter = createUpdateUserDtoToUserBeanDtoConverter;
        this.createUpdateUserMachineDtoToUserMachineBeanDtoConverter = createUpdateUserMachineDtoToUserMachineBeanDtoConverter;
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
            throw new GenericException(ErrorCodes.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public UserMachineBeanResponseDto createUserMachine(CreateUpdateUserMachineDto createUpdateUserMachineDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", zitadelProperties.getGlobalToken());

        HttpEntity<UserBeanMachineDto> request = new HttpEntity<>(createUpdateUserMachineDtoToUserMachineBeanDtoConverter.convert(createUpdateUserMachineDto), headers);

        String url = String.join("", zitadelProperties.getPrimaryDomain(), zitadelProperties.getCreateUserMachineUrl());

        try {
            ResponseEntity<UserMachineBeanResponseDto> responseEntity =
                    restTemplate.exchange(url, HttpMethod.POST, request, UserMachineBeanResponseDto.class);

            UserMachineBeanResponseDto userMachineBeanResponseDto = responseEntity.getBody();

            if (userMachineBeanResponseDto.getUserId() == null) {
                throw new ValidationException("ZITADEL_USER_ID_ERROR");
            }

            SecretUserMachineBeanResponseDto secretUserMachineBeanResponseDto = createSecretUserMachine(userMachineBeanResponseDto.getUserId());
            userMachineBeanResponseDto.setClientId(secretUserMachineBeanResponseDto.getClientId());
            userMachineBeanResponseDto.setClientSecret(secretUserMachineBeanResponseDto.getClientSecret());

            return userMachineBeanResponseDto;
        } catch (Exception e) {
            throw new GenericException(ErrorCodes.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public SecretUserMachineBeanResponseDto createSecretUserMachine(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", zitadelProperties.getGlobalToken());

        HttpEntity<UserBeanMachineDto> request = new HttpEntity<>(headers);

        String url = String.join("", zitadelProperties.getPrimaryDomain(), zitadelProperties.getCreateSecretUserMachineUrl(), userId, "/secret");

        try {
            ResponseEntity<SecretUserMachineBeanResponseDto> responseEntity =
                    restTemplate.exchange(url, HttpMethod.PUT, request, SecretUserMachineBeanResponseDto.class);

            SecretUserMachineBeanResponseDto secretUserMachineBeanResponseDto = responseEntity.getBody();

            if (secretUserMachineBeanResponseDto.getClientId() == null) {
                throw new ValidationException("ZITADEL_CLIENT_ID_ERROR");
            }

            return secretUserMachineBeanResponseDto;
        } catch (Exception e) {
            throw new GenericException(ErrorCodes.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public UserMachineTokenDto getUserMachineToken(String clientId, String clientSecret) {
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
            ResponseEntity<UserMachineTokenDto> responseEntity =
                    restTemplate.exchange("https://s3pro-dev-01-pnmsfb.zitadel.cloud/oauth/v2/token", HttpMethod.POST, entity,
                            UserMachineTokenDto.class);

            UserMachineTokenDto userMachineTokenDto = responseEntity.getBody();

            return userMachineTokenDto;
        } catch (Exception e) {
            throw new GenericException(ErrorCodes.ZITADEL_API_ERROR, e.getMessage(), e);
        }
    }

}
