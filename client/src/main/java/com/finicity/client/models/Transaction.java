package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Created by jhutchins on 9/28/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"accountName"})
public class Transaction {
    private String amount;
    private String bonusAmount;
    private String accountId;
    private String customerId;
    private String createdDate;
    private String description;
    private String escrowAmount;
    private String feeAmount;
    private String id;
    private String institutionTransactionId;
    private String interestAmount;
    private String postedDate;
    private String principalAmount;
    private String status;
    private SubAccount subAccount;
    private String transactionDate;
    private String unitQuantity;
    private String unitValue;

    @Setter
    private String accountName;
}
