package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

/**
 * Created by jhutchins on 9/28/15.
 */
@Data
public class Question {
    private String answer;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Choice> choice;
    private String image;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ImageChoice> imageChoice;
    private String text;
}
