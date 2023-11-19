package org.sid.bankaccountservice.service;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.enums.AccountType;
import org.sid.bankaccountservice.exceptions.BankAccountNotFoundException;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    BankAccountRepository bankAccountRepository;
    AccountMapper accountMapper;
    public AccountServiceImpl(BankAccountRepository bankAccountRepository, AccountMapper accountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public BankAccountResponseDTO getAccountById(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(()->new BankAccountNotFoundException(String.format("Account %s not found", id)));
        return accountMapper.fromBankAccount(bankAccount);
    }

    @Override
    public BankAccountResponseDTO updateAccount(BankAccountRequestDTO bankAccountRequestDTO, String id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(()->new BankAccountNotFoundException(String.format("Account %s not found", id)));
        BeanUtils.copyProperties(bankAccountRequestDTO, bankAccount);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return accountMapper.fromBankAccount(savedAccount);
    }

    @Override
    public List<BankAccountResponseDTO> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(b -> accountMapper.fromBankAccount(b)).collect(Collectors.toList());
    }

    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        BankAccount bankAccount = accountMapper.formBankAccountRequestDTO(bankAccountRequestDTO);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return accountMapper.fromBankAccount(savedAccount);
    }

    @Override
    public void deleteById(String id) throws BankAccountNotFoundException {
        if (bankAccountRepository.findById(id).orElse(null) == null)
            throw new BankAccountNotFoundException(String.format("Not such account %s found", id));
        bankAccountRepository.deleteById(id);
    }

    @Override
    public void populateData() {
        for (int i = 0; i < 10; i++) {
            List<BankAccount> bankAccounts = new ArrayList<>();
                    bankAccounts.add(
                            BankAccount.builder()
                    .Id(i + 1 + "-->" + UUID.randomUUID())
                    .balance(10000 + Math.random() * 90000)
                    .createdAt(new Date())
                    .type(Math.random() < 0.5 ? AccountType.CURRENT_ACCOUNT : AccountType.SAVING_ACCOUNT)
                    .currency("USD")
                    .build()
                    );
            bankAccountRepository.saveAll(bankAccounts);
        }
    }
}
