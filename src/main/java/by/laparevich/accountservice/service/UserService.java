package by.laparevich.accountservice.service;

import by.laparevich.accountservice.dto.mapper.UserMapper;
import by.laparevich.accountservice.dto.request.UserRequestDto;
import by.laparevich.accountservice.dto.response.UserResponseDto;
import by.laparevich.accountservice.entity.User;
import by.laparevich.accountservice.exception.AlreadyExistException;
import by.laparevich.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User userToSave = userMapper.fromUserRequestDto(userRequestDto);

        checkExistence(userToSave);

        UserResponseDto savedUser = userMapper.toUserResponseDto(userRepository.save(userToSave));

        return savedUser;
    }

    private void checkExistence(User user) {
        if (userRepository.existsByDocumentNumber(user.getDocumentNumber())) {
            throw new AlreadyExistException("User with document number: " + user.getDocumentNumber() + " already exists");
        }
    }

}

