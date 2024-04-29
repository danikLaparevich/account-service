package by.laparevich.accountservice.unit.service;

import by.laparevich.accountservice.dto.mapper.UserMapper;
import by.laparevich.accountservice.dto.request.UserRequestDto;
import by.laparevich.accountservice.dto.response.UserResponseDto;
import by.laparevich.accountservice.entity.User;
import by.laparevich.accountservice.exception.AlreadyExistException;
import by.laparevich.accountservice.repository.UserRepository;
import by.laparevich.accountservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_CreateUser_ReturnUserResponseDto() {
        // Arrange
        var userRequestDto = mock(UserRequestDto.class);
        var userToSave = mock(User.class);
        var savedUser = mock(UserResponseDto.class);
        when(userMapper.fromUserRequestDto(userRequestDto)).thenReturn(userToSave);
        when(userRepository.save(userToSave)).thenReturn(userToSave);
        when(userMapper.toUserResponseDto(userToSave)).thenReturn(savedUser);

        // Act
        var result = userService.createUser(userRequestDto);

        // Assert
        assertNotNull(result);
        verify(userMapper).fromUserRequestDto(userRequestDto);
        verify(userRepository).save(userToSave);
        verify(userMapper).toUserResponseDto(userToSave);
    }

    @Test
    void createUser_ThrowsException_WhenUserExists() {
        // Arrange
        String documentNumber = "1234567890";
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setDocumentNumber(documentNumber);

        User userToSave = new User();
        userToSave.setDocumentNumber(documentNumber);

        when(userRepository.existsByDocumentNumber(documentNumber)).thenReturn(true);
        when(userMapper.fromUserRequestDto(userRequestDto)).thenReturn(userToSave);

        // Act and Assert
        AlreadyExistException exception = assertThrows(AlreadyExistException.class, () -> {
            userService.createUser(userRequestDto);
        });

        // Assert
        String expectedErrorMessage = "User with document number: " + documentNumber + " already exists";
        assertEquals(expectedErrorMessage, exception.getMessage());
        verify(userRepository).existsByDocumentNumber(documentNumber);
    }

}
