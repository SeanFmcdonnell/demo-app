package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * Created by jhutchins on 9/29/15.
 */
@Data
@JacksonXmlRootElement(localName = "subscription")
public class Subscription {
    private String id;
    private String accountId;
    private String callbackUrl;
    private String type;
    private String signingKey;
}
