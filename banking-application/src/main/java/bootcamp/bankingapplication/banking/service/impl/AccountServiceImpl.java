package bootcamp.bankingapplication.banking.service.impl;

import bootcamp.bankingapplication.banking.dto.AccountDto;
import bootcamp.bankingapplication.banking.entity.Account;
import bootcamp.bankingapplication.banking.mapper.AccountMapper;
import bootcamp.bankingapplication.banking.repository.AccountRepository;
import bootcamp.bankingapplication.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private AccountRepository accountRepository;
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account); // to save in Db
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }



    @Override
    public AccountDto transfer(Long fromAccountId, Long toAccountId, double amount) {
        AccountDto fromAccount = getAccountById(fromAccountId);
        AccountDto toAccount = getAccountById(toAccountId);

        if (fromAccount == null || toAccount == null) {
            throw new RuntimeException("One or both accounts do not exist.");
        }

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds for transfer.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        deposit(toAccountId, amount);
        withdraw(fromAccountId, amount);

        return fromAccount;
    }

       @Override
        public Map<String, Object> getTransactionsById(Long id) {
            AccountDto account = getAccountById(id);
            if (account == null) {
                throw new RuntimeException("Account does not exist.");
            }

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm (dd-MM-yyyy)");
            String formattedTime = currentTime.format(formatter);

            Map<String, Object> transactionDetails = new HashMap<>();
            transactionDetails.put("message", "Transaction details for account holder: " + account.getAccountHolderName());
            transactionDetails.put("timestamp", formattedTime);

            return transactionDetails;
        }


    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account does not exists"));

        accountRepository.deleteById(id);
    }
}
