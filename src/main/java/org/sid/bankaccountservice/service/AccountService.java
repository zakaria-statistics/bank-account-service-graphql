package org.sid.bankaccountservice.service;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface AccountService {
    BankAccountResponseDTO getAccountById(String id) throws BankAccountNotFoundException;
    BankAccountResponseDTO updateAccount(BankAccountRequestDTO bankAccountRequestDTO, String id) throws BankAccountNotFoundException;
    List<BankAccountResponseDTO> getAllAccounts();
    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountRequestDTO);
    void deleteById(String id) throws BankAccountNotFoundException;
    void populateData();

}
