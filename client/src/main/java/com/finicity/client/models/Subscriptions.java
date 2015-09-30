package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.finicity.client.models.Subscription;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/29/15.
 */
@Data
@JacksonXmlRootElement(localName = "subscriptions")
public class Subscriptions {
    @JsonProperty("subscription")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Subscription> list;
}
