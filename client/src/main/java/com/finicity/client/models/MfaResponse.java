package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jhutchins on 9/30/15.
 */
@Getter
@AllArgsConstructor
@JacksonXmlRootElement(localName = "accounts")
public class MfaResponse {
    private final MfaChallenges mfaChallenges;
}
