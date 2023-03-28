package com.s3procore.service.user;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.model.user.User;
import com.s3procore.repository.UserRepository;
import com.s3procore.service.exception.ValidationException;
import com.s3procore.service.user.client.UserClient;
import com.s3procore.service.user.converter.CreateUpdateUserDtoToEntityConverter;
import com.s3procore.service.user.converter.UserToDtoConverter;
import com.s3procore.service.validation.ValidationCode;
import com.s3procore.service.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final UserClient userClient;
    private final CreateUpdateUserDtoToEntityConverter createUpdateUserDtoToEntityConverter;
    private final UserToDtoConverter userToDtoConverter;

    @Override
    @Transactional
    public UserDto create(CreateUpdateUserDto userDto) {
        validationService.validate(userDto);

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ValidationException(ValidationCode.USER_ALREADY_EXISTS);
        }

        UserBeanResponseDto userBeanResponseDto = userClient.createUser(userDto);

        User user = createUpdateUserDtoToEntityConverter.convert(userDto);
        user.setSubId(userBeanResponseDto.getUserId());
        user.setCreatedDate(userBeanResponseDto.getDetails().getCreationDate());

        return userToDtoConverter.convert(userRepository.save(user));
    }

}
