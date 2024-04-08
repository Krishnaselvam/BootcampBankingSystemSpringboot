package bootcamp.bankingapplication.banking.repository;

import bootcamp.bankingapplication.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
