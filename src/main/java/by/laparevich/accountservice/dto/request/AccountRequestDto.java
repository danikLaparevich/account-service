package by.laparevich.accountservice.dto.request;

import by.laparevich.accountservice.entity.DocumentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AccountRequestDto {
    @NotNull(message = "Account balance is required")
    @DecimalMin(value = "0", inclusive = true, message = "Balance must be greater than 0")
    private BigDecimal balance;

    @NotNull(message = "Account currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must consist of 3 uppercase Latin letters")
    private String currency;

    @NotNull(message = "User id is required")
    private Long userId;
}