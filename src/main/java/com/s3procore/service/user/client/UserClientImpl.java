package com.s3procore.service.user.client;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.bean.UserBeanDto;
import com.s3procore.dto.user.bean.UserBeanResponseDto;
import com.s3procore.service.config.ZitadelProperties;
import com.s3procore.service.user.converters.CreateUpdateUserDtoToUserBeanDtoConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserClientImpl implements UserClient {

    private final RestTemplate restTemplate;
    private final ZitadelProperties zitadelProperties;
    private final CreateUpdateUserDtoToUserBeanDtoConverter createUpdateUserDtoToUserBeanDtoConverter;

    public UserClientImpl(RestTemplateBuilder restTemplateBuilder,
                          ZitadelProperties zitadelProperties,
                          CreateUpdateUserDtoToUserBeanDtoConverter createUpdateUserDtoToUserBeanDtoConverter) {
        this.restTemplate = restTemplateBuilder.build();
        this.zitadelProperties = zitadelProperties;
        this.createUpdateUserDtoToUserBeanDtoConverter = createUpdateUserDtoToUserBeanDtoConverter;
    }

    @Override
    public UserBeanResponseDto createUser(CreateUpdateUserDto createUpdateUserDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", zitadelProperties.getGlobalToken());

        HttpEntity<UserBeanDto> request = new HttpEntity<>(createUpdateUserDtoToUserBeanDtoConverter.convert(createUpdateUserDto), headers);

        String url = String.join("", zitadelProperties.getPrimaryDomain(), zitadelProperties.getCreateUserUrl());

        ResponseEntity<UserBeanResponseDto> responseEntity =
                restTemplate.exchange(url, HttpMethod.POST, request, UserBeanResponseDto.class);

        UserBeanResponseDto userBeanResponseDto = responseEntity.getBody();

        if (userBeanResponseDto.getUserId() == null) {
            throw new RuntimeException();//todo
        }

        return userBeanResponseDto;
    }

}
