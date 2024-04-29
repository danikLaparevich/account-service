package by.laparevich.accountservice.dto.mapper;


import by.laparevich.accountservice.dto.request.AccountRequestDto;
import by.laparevich.accountservice.dto.response.AccountResponseDto;
import by.laparevich.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "user.id", target = "userId")
    AccountResponseDto toAccountResponseDto(Account account);

    Account fromAccountRequestDto(AccountRequestDto accountRequestDto);

}

