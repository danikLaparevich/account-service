package by.laparevich.accountservice.integration;

import by.laparevich.accountservice.EntityUtil;
import by.laparevich.accountservice.dto.request.AccountRequestDto;
import by.laparevich.accountservice.dto.request.UserRequestDto;
import by.laparevich.accountservice.dto.response.AccountResponseDto;
import by.laparevich.accountservice.dto.response.UserResponseDto;
import by.laparevich.accountservice.entity.DocumentType;
import by.laparevich.accountservice.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;

@Testcontainers
@TestConfiguration(proxyBeanMethods = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountServiceIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15.1-alpine")
    );

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepository accountRepository;

    @Autowired
    UserRepository transactionRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        userRepository.deleteAll();
        accountRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    public void testCreateNotExistingUser() {
        UserRequestDto userRequestDto = EntityUtil.getUserRequestDto();
        UserResponseDto userResponseDto = EntityUtil.getUserResponseDto();

        given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(1)
                )
                .body(
                        "name",
                        Matchers.equalTo(userResponseDto.getName())
                )
                .body(
                        "documentNumber",
                        Matchers.equalTo(userResponseDto.getDocumentNumber())
                )
                .body(
                        "documentType",
                        Matchers.equalTo(String.valueOf(userResponseDto.getDocumentType()))
                );
    }

    @Test
    public void testCreateExistingUser() {
        UserRequestDto userRequestDto = EntityUtil.getUserRequestDto();
        UserResponseDto userResponseDto = EntityUtil.getUserResponseDto();

        given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(1)
                )
                .body(
                        "name",
                        Matchers.equalTo(userResponseDto.getName())
                )
                .body(
                        "documentNumber",
                        Matchers.equalTo(userResponseDto.getDocumentNumber())
                )
                .body(
                        "documentType",
                        Matchers.equalTo(String.valueOf(userResponseDto.getDocumentType()))
                );

        given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(
                        HttpStatus.CONFLICT.value()
                )
                .body(
                        "message",
                        Matchers.equalTo("User with document number: " + userResponseDto.getDocumentNumber() + " already exists")
                );
    }

    @Test
    public void testCreateNotExistingAccount() {
        AccountRequestDto accountRequestDto = EntityUtil.getAccountRequestDto();
        AccountResponseDto accountResponseDto = EntityUtil.getAccountResponseDto();

        UserRequestDto userRequestDto = EntityUtil.getUserRequestDto();
        UserResponseDto userResponseDto = EntityUtil.getUserResponseDto();

        given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(1)
                )
                .body(
                        "name",
                        Matchers.equalTo(userResponseDto.getName())
                )
                .body(
                        "documentNumber",
                        Matchers.equalTo(userResponseDto.getDocumentNumber())
                )
                .body(
                        "documentType",
                        Matchers.equalTo(String.valueOf(userResponseDto.getDocumentType()))
                );

        given()
                .contentType(ContentType.JSON)
                .body(accountRequestDto)
                .when()
                .post("/api/v1/accounts")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(1)
                )
                .body(
                        "balance",
                        Matchers.equalTo(0)
                )
                .body(
                        "currency",
                        Matchers.equalTo(accountResponseDto.getCurrency())
                )
                .body(
                        "userId",
                        Matchers.equalTo(1)
                );
    }

    @Test
    public void depositToAccount_Successful() {
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("10");

        AccountRequestDto accountRequestDto = EntityUtil.getAccountRequestDto();
        AccountResponseDto accountResponseDto = EntityUtil.getAccountResponseDto();

        UserRequestDto userRequestDto = EntityUtil.getUserRequestDto();
        UserResponseDto userResponseDto = EntityUtil.getUserResponseDto();

        given()
                .contentType(ContentType.JSON)
                .body(userRequestDto)
                .when()
                .post("/api/v1/users")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(1)
                )
                .body(
                        "name",
                        Matchers.equalTo(userResponseDto.getName())
                )
                .body(
                        "documentNumber",
                        Matchers.equalTo(userResponseDto.getDocumentNumber())
                )
                .body(
                        "documentType",
                        Matchers.equalTo(String.valueOf(userResponseDto.getDocumentType()))
                );

        given()
                .contentType(ContentType.JSON)
                .body(accountRequestDto)
                .when()
                .post("/api/v1/accounts")
                .then()
                .statusCode(
                        HttpStatus.CREATED.value()
                )
                .body(
                        "id",
                        Matchers.equalTo(1)
                )
                .body(
                        "balance",
                        Matchers.equalTo(0)
                )
                .body(
                        "currency",
                        Matchers.equalTo(accountResponseDto.getCurrency())
                )
                .body(
                        "userId",
                        Matchers.equalTo(1)
                );

        given()
                .contentType(ContentType.JSON)
                .pathParam("accountId", accountId)
                .queryParam("amount", amount)
                .when()
                .post("/api/v1/accounts/{accountId}/deposit")
                .then()
                .statusCode(200)
                .body(equalTo("Account successfully deposited"));
    }

    @Test
    public void depositToAccount_InternalServerError() {
        Long accountId = 2L;

        given()
                .contentType(ContentType.JSON)
                .pathParam("accountId", accountId)
                .queryParam("amount", 10)
                .when()
                .post("/api/v1/accounts/{accountId}/deposit")
                .then()
                .statusCode(500)
                .body(equalTo("Error depositing to account: Account not found"));
    }

}

