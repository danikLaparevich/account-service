package by.laparevich.accountservice.unit.service;

import by.laparevich.accountservice.dto.mapper.AccountMapper;
import by.laparevich.accountservice.dto.request.AccountRequestDto;
import by.laparevich.accountservice.dto.response.AccountResponseDto;
import by.laparevich.accountservice.entity.Account;
import by.laparevich.accountservice.entity.Transaction;
import by.laparevich.accountservice.entity.User;
import by.laparevich.accountservice.exception.ResourceNotFoundException;
import by.laparevich.accountservice.repository.AccountRepository;
import by.laparevich.accountservice.repository.TransactionRepository;
import by.laparevich.accountservice.repository.UserRepository;
import by.laparevich.accountservice.service.AccountService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void AccountService_CreateAccount_ReturnAccountResponseDto() {
        // Arrange
        AccountRequestDto accountRequestDto = mock(AccountRequestDto.class);
        Account accountToSave = mock(Account.class);
        User user = mock(User.class);
        AccountResponseDto savedAccount = mock(AccountResponseDto.class);

        when(accountMapper.fromAccountRequestDto(accountRequestDto)).thenReturn(accountToSave);
        when(userRepository.findById(accountRequestDto.getUserId())).thenReturn(Optional.of(user));
        when(accountRepository.save(accountToSave)).thenReturn(accountToSave);
        when(accountMapper.toAccountResponseDto(accountToSave)).thenReturn(savedAccount);

        // Act
        AccountResponseDto result = accountService.createAccount(accountRequestDto);

        // Assert
        assertNotNull(result);
        verify(accountMapper).fromAccountRequestDto(accountRequestDto);
        verify(userRepository).findById(accountRequestDto.getUserId());
        verify(accountRepository).save(accountToSave);
        verify(accountMapper).toAccountResponseDto(accountToSave);
    }

    @Test
    public void AccountService_CreateAccount_ResourceNotFoundException() {
        // Arrange
        Long userId = 1L;
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountService.createAccount(accountRequestDto);
        });

        // Assert
        String expectedErrorMessage = "There isn't user with id : " + userId;
        assertEquals(expectedErrorMessage, exception.getMessage());
        verify(userRepository).findById(userId);
    }

    @Test
    public void depositToAccount_Successfully() {
        // Arrange
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("100");
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(new BigDecimal("500"));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        accountService.depositToAccount(accountId, amount);

        // Assert
        verify(entityManager).lock(account, LockModeType.OPTIMISTIC);
        verify(accountRepository).save(account);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void depositToAccount_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("100");
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.depositToAccount(accountId, amount);
        });

        // Assert
        assertEquals("Account not found", exception.getMessage());
        verifyNoInteractions(entityManager);
        verifyNoInteractions(transactionRepository);
    }
}
