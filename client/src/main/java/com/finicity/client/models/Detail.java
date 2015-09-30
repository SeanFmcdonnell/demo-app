package com.finicity.client.models;

import lombok.Data;

/**
 * Created by jhutchins on 9/30/15.
 */
@Data
public class Detail {
    private String paymentMinAmount;
    private String statementCloseBalance;
    private String paymentDueDate;
    private String statementEndDate;
}
