package com.finicity.client.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jhutchins on 9/30/15.
 */
@Getter
@Builder(builderClassName = "Builder")
public class ActivationResponse {
    @Setter
    private ActivationResponseBody body;
    private final String mfaSession;
}
