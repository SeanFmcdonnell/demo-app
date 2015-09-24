package com.finicity.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/24/15.
 */
@Data
@JacksonXmlRootElement(localName = "institutions")
public class Institutions {
    private int found;
    private int displaying;
    private boolean moreAvailable;
    private String createdDate;
    @JsonProperty("institution")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<Institution> list;
}
