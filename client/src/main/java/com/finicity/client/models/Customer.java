package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * Created by jhutchins on 9/25/15.
 */
@Data
@JacksonXmlRootElement(localName = "customer")
public class Customer {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String type;
    private String createdDate;
}
