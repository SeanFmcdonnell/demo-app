package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/26/15.
 */
@Data
@JacksonXmlRootElement(localName = "accounts")
public class AccountCredentials {
    @JsonProperty("loginField")
    @JacksonXmlElementWrapper(localName = "credentials")
    List<LoginField> list;
}
