package by.laparevich.accountservice;

import by.laparevich.accountservice.dto.request.AccountRequestDto;
import by.laparevich.accountservice.dto.request.UserRequestDto;
import by.laparevich.accountservice.dto.response.AccountResponseDto;
import by.laparevich.accountservice.dto.response.UserResponseDto;
import by.laparevich.accountservice.entity.DocumentType;

import java.math.BigDecimal;

public class EntityUtil {

    public static UserRequestDto getUserRequestDto() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Daniil")
                .documentNumber("MC1234567")
                .documentType("PASSPORT")
                .build();
        return userRequestDto;
    }

    public static UserResponseDto getUserResponseDto() {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("Daniil")
                .documentNumber("MC1234567")
                .documentType(DocumentType.PASSPORT)
                .build();
        return userResponseDto;
    }

    public static AccountRequestDto getAccountRequestDto() {
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .balance(BigDecimal.ZERO)
                .currency("BYN")
                .userId(1L)
                .build();
        return accountRequestDto;
    }

    public static AccountResponseDto getAccountResponseDto() {
        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(1L)
                .balance(BigDecimal.ZERO)
                .currency("BYN")
                .userId(1L)
                .build();
        return accountResponseDto;
    }

}
