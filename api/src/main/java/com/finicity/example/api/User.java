package com.finicity.example.api;

import lombok.Builder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jhutchins on 9/25/15.
 */
@Builder
public class User {
    @NotEmpty
    private final String finicityId;
    @NotEmpty
    private final String googleId;
    private final String email;
}
