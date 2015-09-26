package com.finicity.example.factories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finicity.client.FinicityClient;
import com.finicity.example.resources.GoogleResource;
import com.finicity.example.services.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Arrays;

/**
 * Created by jhutchins on 9/25/15.
 */
public class GoogleResourceFactory {
    @NotEmpty
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonProperty))
    private String clientId;

    @NotEmpty
    @Getter(onMethod = @__(@JsonProperty))
    @Setter(onMethod = @__(@JsonProperty))
    private String secret;

    public GoogleResource build(AuthService auth, FinicityClient client) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList(getClientId()))
                .build();
        return GoogleResource.builder()
                .auth(auth)
                .client(client)
                .verifier(verifier)
                .build();

    }
}
