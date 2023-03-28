package com.s3procore.dto.application;

import com.s3procore.model.application.ApplicationGrantType;
import com.s3procore.model.application.ApplicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

    private Long id;

    private ApplicationType applicationType;

    private String applicationName;

    private String clientId;

    private String clientSecret;

    private ApplicationGrantType applicationGrantType;

}
