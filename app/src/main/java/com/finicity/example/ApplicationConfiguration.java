package com.finicity.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by jhutchins on 9/23/15.
 */
public class ApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    @Getter(onMethod = @__(@JsonProperty("finicityClient")))
    @Setter(onMethod = @__(@JsonProperty("finicityClient")))
    private FinicityClientFactory finicityClient = new FinicityClientFactory();
}
