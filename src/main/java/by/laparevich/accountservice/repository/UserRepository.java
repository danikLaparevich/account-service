package by.laparevich.accountservice.repository;

import by.laparevich.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByDocumentNumber(String documentNumber);
}