package org.sid.bankaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bankaccountservice.entities.Customer;
import org.sid.bankaccountservice.enums.AccountType;

import java.util.Date;
@Data @NoArgsConstructor @AllArgsConstructor @Builder

public class BankAccountResponseDTO {

    private String Id;
    private Date createdAt;
    private Double balance;
    private String currency;
    private AccountType type;
    private Customer customer;
}
