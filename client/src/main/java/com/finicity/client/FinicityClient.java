package com.finicity.client;

import lombok.Builder;

/**
 * Created by jhutchins on 9/23/15.
 */
@Builder
public class FinicityClient {
    private final String partnerId;
    private final String partnerSecret;
    private final String finicityAppKey;
}
