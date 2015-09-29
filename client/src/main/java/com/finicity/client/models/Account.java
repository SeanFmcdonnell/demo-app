package com.finicity.client.models;

import lombok.Data;

/**
 * Created by jhutchins on 9/26/15.
 */
@Data
public class Account {
    private String id;
    private String number;
    private String name;
    private String type;
    private String status;
    private String balance;
    private String aggregationStatusCode;
    private String customerId;
    private String institutionId;
    private String balanceDate;
    private String aggregationSuccessDate;
    private String aggregationAttemptDate;
    private String createdDate;
    private String lastUpdatedDate;
    private String detail;
}
