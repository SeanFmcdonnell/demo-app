package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.finicity.client.FinicityClient;
import lombok.Data;
import org.glassfish.jersey.media.sse.EventOutput;

import java.util.Map;

/**
 * Created by ckuhn on 10/8/15.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransactionEvent.class, name = "transaction"),
        @JsonSubTypes.Type(value = AccountEvent.class, name = "account")
})
@Data
public abstract class Event {
    @JsonProperty("type")
    protected String type;
    @JsonProperty("class")
    protected String kind;

    public abstract void send(Map<String, EventOutput> outputs, FinicityClient client);
}
