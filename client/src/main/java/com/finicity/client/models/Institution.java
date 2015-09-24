package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jhutchins on 9/24/15.
 */
@Data
@EqualsAndHashCode
@JacksonXmlRootElement(localName = "institution")
public class Institution {
    private int id;
    private String name;
    private String accountTypeDescription;
    private String urlHomeApp;
    private String urlLogonApp;
    private String urlProductApp;
}
