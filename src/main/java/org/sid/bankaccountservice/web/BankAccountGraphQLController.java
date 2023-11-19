package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.exceptions.BankAccountNotFoundException;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    BankAccountRepository bankAccountRepository;

    public BankAccountGraphQLController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    @QueryMapping
    public List<BankAccount> accountList(){
        return bankAccountRepository.findAll();
    }
    @QueryMapping
    public BankAccount bankAccountById(@Argument String id) throws BankAccountNotFoundException {
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new BankAccountNotFoundException(String.format("No such account %s founded", id)));
    }
}
