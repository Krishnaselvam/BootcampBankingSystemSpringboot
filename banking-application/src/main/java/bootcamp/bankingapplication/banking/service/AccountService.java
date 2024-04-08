package bootcamp.bankingapplication.banking.service;

import bootcamp.bankingapplication.banking.dto.AccountDto;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    AccountDto transfer(Long fromAccountId, Long toAccountId, double amount);

    Map<String, Object> getTransactionsById(Long id);
    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

}
