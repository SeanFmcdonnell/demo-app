package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/24/15.
 */
@Data
public class LoginForm {
    @JsonProperty("loginField")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<LoginField> fields;
}
