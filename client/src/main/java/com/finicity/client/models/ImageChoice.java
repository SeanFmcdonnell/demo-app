package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by jhutchins on 9/30/15.
 */
@Data
@EqualsAndHashCode
@JacksonXmlRootElement(localName = "imageChoice")
public class ImageChoice {
    @JacksonXmlProperty(isAttribute = true)
    private String value;
    @JacksonXmlText
    private String data;
}
