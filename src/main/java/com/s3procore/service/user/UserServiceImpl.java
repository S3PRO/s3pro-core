package com.s3procore.service.user;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.dto.user.bean.UserBeanResponseDto;
import com.s3procore.model.User;
import com.s3procore.repository.UserRepository;
import com.s3procore.service.exception.EntityAlreadyExistsException;
import com.s3procore.service.user.client.UserClient;
import com.s3procore.service.user.converters.CreateUpdateUserDtoToEntityConverter;
import com.s3procore.service.user.converters.UserToDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserClient userClient;
    private final CreateUpdateUserDtoToEntityConverter createUpdateUserDtoToEntityConverter;
    private final UserToDtoConverter userToDtoConverter;

    @Override
    @Transactional
    public UserDto create(CreateUpdateUserDto newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("User already exists!");
        }

        UserBeanResponseDto userBeanResponseDto = userClient.createUser(newUser);

        User user = createUpdateUserDtoToEntityConverter.convert(newUser);
        user.setZitadelUserId(userBeanResponseDto.getUserId());
        user.setResourceOwner(userBeanResponseDto.getDetails().getResourceOwner());
        user.setCreatedDate(userBeanResponseDto.getDetails().getCreationDate());

        user = userRepository.save(user);

        return userToDtoConverter.convert(user);
    }
}
