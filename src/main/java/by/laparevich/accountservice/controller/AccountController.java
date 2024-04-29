package by.laparevich.accountservice.controller;

import by.laparevich.accountservice.dto.request.AccountRequestDto;
import by.laparevich.accountservice.dto.response.AccountResponseDto;
import by.laparevich.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;


    @Operation(summary = "Deposit to user account", description = "Deposit to user account", tags = { "account" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deposited"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> depositToAccount(@PathVariable("accountId") Long accountId, @RequestParam("amount") BigDecimal amount) {
        try {
            accountService.depositToAccount(accountId, amount);
            return ResponseEntity.ok("Account successfully deposited");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error depositing to account: " + e.getMessage());
        }
    }

    @Operation(summary = "Add new account", description = "Add new account", tags = { "account" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        AccountResponseDto createdAccount = accountService.createAccount(accountRequestDto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

}
