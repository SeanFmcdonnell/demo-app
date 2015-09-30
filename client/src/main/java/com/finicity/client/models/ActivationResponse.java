package com.finicity.client.models;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by jhutchins on 9/30/15.
 */
@Getter
@Builder(builderClassName = "Builder")
public class ActivationResponse {
    private final ActivationResponseBody body;
    private final String mfaSession;
}
