package by.laparevich.accountservice.dto.response;

import by.laparevich.accountservice.entity.DocumentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserResponseDto {
    private Long id;
    private String name;
    private String documentNumber;
    private DocumentType documentType;
}
