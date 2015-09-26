package com.finicity.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finicity.example.factories.FinicityClientFactory;
import com.finicity.example.factories.GoogleResourceFactory;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by jhutchins on 9/23/15.
 */
public class ExampleConfiguration extends Configuration {
    @Valid
    @NotNull
    @Getter(onMethod = @__(@JsonProperty("finicityClient")))
    @Setter(onMethod = @__(@JsonProperty("finicityClient")))
    private FinicityClientFactory finicityClient = new FinicityClientFactory();

    @Valid
    @NotNull
    @Getter(onMethod = @__(@JsonProperty("google")))
    @Setter(onMethod = @__(@JsonProperty("google")))
    private GoogleResourceFactory googleReource = new GoogleResourceFactory();

    @Valid
    @NotNull
    @Getter(onMethod = @__(@JsonProperty("httpClient")))
    private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
}
