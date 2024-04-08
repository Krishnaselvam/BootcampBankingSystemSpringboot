package bootcamp.bankingapplication.banking.controller;

import bootcamp.bankingapplication.banking.dto.AccountDto;
import bootcamp.bankingapplication.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add Account REST API

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get Account REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //Deposit REST API
    @PutMapping("/{id}/deposit") //put mapping notation will use output mapping annotation to map incoming HTTP
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){ //use path variable anotation to bind value of this template URL variable to this method argument and the requestbody annotation will map the request Json body into this map java object

        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);
        return ResponseEntity.ok(accountDto);
    }

    //Withdraw Rest API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double>request){
        double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return ResponseEntity.ok(accountDto);
    }


    @PutMapping("/{fromId}/transfer/{toId}")
    public ResponseEntity<AccountDto> transfer(
            @PathVariable Long fromId,
            @PathVariable Long toId,
            @RequestBody Map<String, Double> request) {

        double amount = request.get("amount");
        AccountDto fromAccount = accountService.transfer(fromId, toId, amount);
        return ResponseEntity.ok(fromAccount);
    }


    @GetMapping("/{id}/transactions")
    public ResponseEntity<Map<String, Object>> getTransactionsById(@PathVariable Long id) {
        Map<String, Object> transactionDetails = accountService.getTransactionsById(id);
        return ResponseEntity.ok(transactionDetails);
    }

    //Get All Account REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }


    //Delete Account REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully!");
    }
}
