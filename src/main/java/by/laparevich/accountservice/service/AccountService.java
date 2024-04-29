package by.laparevich.accountservice.service;

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
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final UserRepository userRepository;

    private final AccountMapper accountMapper;

    private final EntityManager entityManager;

    @Transactional
    public void depositToAccount(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        entityManager.lock(account, LockModeType.OPTIMISTIC);

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        account.setBalance(newBalance);

        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepository.save(transaction);
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {

        Account accountToSave = accountMapper.fromAccountRequestDto(accountRequestDto);

        User user = userRepository.findById(accountRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("There isn't user with id : " + accountRequestDto.getUserId()));

        accountToSave.setUser(user);

        AccountResponseDto savedAccount = accountMapper.toAccountResponseDto(accountRepository.save(accountToSave));

        return savedAccount;
    }

}
