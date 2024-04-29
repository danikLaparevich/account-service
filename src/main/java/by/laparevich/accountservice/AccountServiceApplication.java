package by.laparevich.accountservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "account-service REST API",
                version = "1.0.0",
                description = "1. Добавление нового пользователя;\n" +
                        "2. Добавление нового счета;\n" +
                        "3. Пополнение счета;",
                contact = @Contact(
                        name = "Daniil Laparevich",
                        email = "danik.laparevich@bk.ru"
                )
        )
)
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
