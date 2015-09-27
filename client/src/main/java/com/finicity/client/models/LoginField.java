package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * Created by jhutchins on 9/24/15.
 */
@Data
@JacksonXmlRootElement(localName = "loginField")
public class LoginField {
    private int id;
    private String name;
    private String value;
    private String description;
    private int displayOrder;
    private boolean mask;
    private int valueLengthMin;
    private int valueLengthMax;
    private String instructions;
}
