package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/29/15.
 */
@Data
@JacksonXmlRootElement(localName = "event")
public class Event {
    private String type;
    @JsonProperty("class")
    private String kind;
    @JacksonXmlElementWrapper(localName = "records")
    private List<Transaction> transaction;
}
