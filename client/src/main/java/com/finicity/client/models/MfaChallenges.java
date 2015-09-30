package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/28/15.
 */
@Data
@JacksonXmlRootElement(localName = "mfaChallenges")
public class MfaChallenges implements ActivationResponseBody {
    @JsonProperty("question")
    @JacksonXmlElementWrapper(localName = "questions")
    private List<Question> list;
}
