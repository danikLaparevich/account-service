package by.laparevich.accountservice.dto.request;

import by.laparevich.accountservice.entity.DocumentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserRequestDto {
    @NotNull(message = "User name is required")
    private String name;
    @NotNull(message = "Document number is required")
    private String documentNumber;
    @NotNull(message = "Document type is required")
    @Pattern(regexp = "PASSPORT|DRIVER_LISENCE", message = "Document type must be PASSPORT or DRIVER_LISENCE")
    private String documentType;
}
