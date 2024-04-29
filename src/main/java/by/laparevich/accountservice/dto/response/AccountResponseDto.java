package by.laparevich.accountservice.dto.response;

import by.laparevich.accountservice.entity.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AccountResponseDto {
    private Long id;
    private BigDecimal balance;
    private String currency;
    private Long userId;
}

