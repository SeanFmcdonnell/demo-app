package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * Created by jhutchins on 9/24/15.
 */
@Data
@JacksonXmlRootElement(localName = "access")
public class Access {
    private String token;
}
