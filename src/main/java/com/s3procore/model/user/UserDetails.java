package com.s3procore.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDetails {

    private Long id;

    private Long companyId;

}
