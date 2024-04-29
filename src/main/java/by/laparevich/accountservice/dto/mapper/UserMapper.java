package by.laparevich.accountservice.dto.mapper;

import by.laparevich.accountservice.dto.request.UserRequestDto;
import by.laparevich.accountservice.dto.response.UserResponseDto;
import by.laparevich.accountservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDto toUserResponseDto(User user);

    User fromUserRequestDto(UserRequestDto userRequestDto);

}

