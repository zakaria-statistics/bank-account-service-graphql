package org.sid.bankaccountservice.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RequestMapping("/api")
public class AccountRestController {
    BankAccountRepository bankAccountRepository;
    public AccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return bankAccountRepository.findAll();
    }
    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found", id)));
    }
    @PostMapping("/bankAccounts")
    public BankAccount saveAccount(@RequestBody BankAccount bankAccount){
        if (bankAccount.getId() == null)
            bankAccount.setId(UUID.randomUUID().toString());
        if (bankAccount.getCreatedAt() == null)
            bankAccount.setCreatedAt(new Date());
        return bankAccountRepository.save(bankAccount);
    }
    @PutMapping("/bankAccounts/{id}")
    public BankAccount updateAccount(@RequestBody BankAccount bankAccount, @PathVariable String id){
        BankAccount bankAccount1 = bankAccount(id);
        if (bankAccount.getBalance() != null)
            bankAccount1.setBalance(bankAccount.getBalance());
        if (bankAccount.getCurrency() != null)
            bankAccount1.setCurrency(bankAccount.getCurrency());
        if (bankAccount.getType() != null)
            bankAccount1.setType(bankAccount.getType());
        if (bankAccount.getCreatedAt() != null)
            bankAccount1.setCreatedAt(new Date());
        return bankAccountRepository.save(bankAccount1);
    }
    @DeleteMapping("/bankAccounts/{id}")
    public void deleteAccount(@PathVariable String id){
        BankAccount bankAccount = bankAccount(id);
        bankAccountRepository.deleteById(id);
    }
}
