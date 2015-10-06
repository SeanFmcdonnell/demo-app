package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

/**
 * Created by ckuhn on 10/6/15.
 */
@Data
@Builder
public class AuthResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("activeId")
    private String activeId;

    @JsonProperty("testingId")
    private String testingId;
}
