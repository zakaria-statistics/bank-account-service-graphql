package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.exceptions.BankAccountNotFoundException;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AccountRestController {
    BankAccountRepository bankAccountRepository;
    AccountService accountService;
    public AccountRestController(BankAccountRepository bankAccountRepository, AccountService accountService) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
    }

    @GetMapping("/bankAccounts") //refactoring done
    public List<BankAccountResponseDTO> bankAccounts(){
        return accountService.getAllAccounts();
    }
    @GetMapping("/bankAccounts/{id}")// refactoring done
    public BankAccountResponseDTO bankAccount(@PathVariable String id) throws BankAccountNotFoundException {
        return accountService.getAccountById(id);
    }
    @PostMapping("/bankAccounts") //refactoring done
    public BankAccountResponseDTO saveAccount(@RequestBody BankAccountRequestDTO bankAccountRequestDTO){
        return accountService.addAccount(bankAccountRequestDTO);
    }
    @PutMapping("/bankAccounts/{id}")//refactoring done
    public BankAccountResponseDTO updateAccount(@RequestBody BankAccountRequestDTO bankAccountRequestDTO, @PathVariable String id) throws BankAccountNotFoundException {
        return accountService.updateAccount(bankAccountRequestDTO, id);
    }
    @DeleteMapping("/bankAccounts/{id}")//refactoring done
    public void deleteAccount(@PathVariable String id) throws BankAccountNotFoundException {
        accountService.deleteById(id);
    }
}
