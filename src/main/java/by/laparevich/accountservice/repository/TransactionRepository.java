package by.laparevich.accountservice.repository;

import by.laparevich.accountservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
