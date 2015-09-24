package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * Created by jhutchins on 9/24/15.
 */
@Getter
@Builder
@JacksonXmlRootElement(localName = "credentials")
public class Credentials {
    @NotNull
    private final String partnerId;

    @NotNull
    private final String partnerSecret;
}
