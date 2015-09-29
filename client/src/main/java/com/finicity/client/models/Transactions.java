package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/28/15.
 */
@Data
@JacksonXmlRootElement(localName = "transactions")
public class Transactions {
    private int found;
    private int displaying;
    private boolean moreAvailable;
    private String createdDate;
    @JsonProperty("transaction")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<Transaction> list;
}
