package com.finicity.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finicity.client.FinicityClient;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jhutchins on 9/23/15.
 */
public class FinicityClientFactory {
    @NotEmpty
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonProperty))
    private String partnerId;

    @NotEmpty
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonProperty))
    private String partnerSecret;

    @NotEmpty
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonProperty))
    private String finicityAppKey;

    public FinicityClient build() {
        FinicityClient client = FinicityClient.builder()
                .finicityAppKey(getFinicityAppKey())
                .partnerId(getPartnerId())
                .partnerSecret(getPartnerSecret())
                .build();
        return client;
    }
}
