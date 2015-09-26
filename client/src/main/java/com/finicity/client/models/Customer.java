package com.finicity.client.models;

import lombok.Data;

/**
 * Created by jhutchins on 9/25/15.
 */
@Data
public class Customer {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String type;
    private String createdDate;
}
