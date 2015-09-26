package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/24/15.
 */
@Data
@JacksonXmlRootElement(localName = "customers")
public class Customers {
    private int found;
    private int displaying;
    private boolean moreAvailable;
    @JsonProperty("customer")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<Customer> list;
}
