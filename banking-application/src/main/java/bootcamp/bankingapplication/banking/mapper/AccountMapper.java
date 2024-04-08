package bootcamp.bankingapplication.banking.mapper;

import bootcamp.bankingapplication.banking.dto.AccountDto;
import bootcamp.bankingapplication.banking.entity.Account;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto){
     
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );

        return account;
    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(
          account.getId(),
          account.getAccountHolderName(),
          account.getBalance()
        );
        return accountDto;
    }
}
