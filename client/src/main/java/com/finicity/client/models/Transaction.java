package com.finicity.client.models;

import lombok.Data;

/**
 * Created by jhutchins on 9/28/15.
 */
@Data
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
}
