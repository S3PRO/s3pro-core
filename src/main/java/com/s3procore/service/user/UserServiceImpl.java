package com.s3procore.service.user;

import com.s3procore.dto.user.CreateUpdateUserDto;
import com.s3procore.dto.user.CreateUpdateUserMachineDto;
import com.s3procore.dto.user.GenerateUserMachineTokenDto;
import com.s3procore.dto.user.UserDto;
import com.s3procore.dto.user.UserMachineTokenDto;
import com.s3procore.dto.user.bean.user.UserBeanResponseDto;
import com.s3procore.dto.user.bean.usermachine.UserMachineBeanResponseDto;
import com.s3procore.model.Company;
import com.s3procore.model.user.User;
import com.s3procore.model.user.UserType;
import com.s3procore.repository.UserRepository;
import com.s3procore.service.exception.EntityAlreadyExistsException;
import com.s3procore.service.user.client.UserClient;
import com.s3procore.service.user.converters.CreateUpdateUserDtoToEntityConverter;
import com.s3procore.service.user.converters.UserToDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserClient userClient;
    private final CreateUpdateUserDtoToEntityConverter createUpdateUserDtoToEntityConverter;
    private final UserToDtoConverter userToDtoConverter;

    @Override
    @Transactional
    public UserDto create(CreateUpdateUserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("User already exists!");
        }

        UserBeanResponseDto userBeanResponseDto = userClient.createUser(userDto);

        Company company = new Company();
        company.setName(userDto.getCompanyName());
        company.setSize(userDto.getCompanySize());

        User user = createUpdateUserDtoToEntityConverter.convert(userDto);
        user.setZitadelUserId(userBeanResponseDto.getUserId());
        user.setZitadelResourceOwner(userBeanResponseDto.getDetails().getResourceOwner());
        user.setCreatedDate(userBeanResponseDto.getDetails().getCreationDate());
        user.setCompany(company);

        user = userRepository.save(user);

        return userToDtoConverter.convert(user);
    }

    @Override
    @Transactional
    public UserMachineBeanResponseDto createUserMachine(CreateUpdateUserMachineDto userMachineDto) {

        UserMachineBeanResponseDto userMachineBeanResponseDto = userClient.createUserMachine(userMachineDto);

        Company company = new Company();
        company.setName(userMachineDto.getCompanyName());
        company.setSize(userMachineDto.getCompanySize());

        User user = new User();
        user.setType(UserType.SERVICE_USER);
        user.setLang(new Locale("en"));
        user.setZitadelUserId(userMachineBeanResponseDto.getUserId());
        user.setZitadelResourceOwner(userMachineBeanResponseDto.getDetails().getResourceOwner());
        user.setCreatedDate(LocalDateTime.now());
        user.setCompany(company);

        user = userRepository.save(user);

        UserMachineTokenDto userMachineTokenDto = userClient.getUserMachineToken(userMachineBeanResponseDto.getClientId(), userMachineBeanResponseDto.getClientSecret());

        userMachineBeanResponseDto.setToken(userMachineTokenDto);

        return userMachineBeanResponseDto;
    }

    @Override
    @Transactional
    public UserMachineTokenDto generateUserMachineToken(GenerateUserMachineTokenDto generateUserMachineTokenDto) {
        UserMachineTokenDto userMachineToken = userClient.getUserMachineToken(generateUserMachineTokenDto.getClientId(), generateUserMachineTokenDto.getClientSecret());

        return userMachineToken;
    }
}