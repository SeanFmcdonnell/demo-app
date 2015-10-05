package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by ckuhn on 10/2/15.
 */
@Data
@Builder
@JacksonXmlRootElement(localName = "error")
public class ErrorMessage implements ActivationResponseBody {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;
}
