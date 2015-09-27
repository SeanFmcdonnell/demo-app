package com.finicity.example.api;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by jhutchins on 9/25/15.
 */
@Getter
@Builder
public class User {
    @NotEmpty
    private final String finicityId;
    @NotEmpty
    private final String googleId;
    private final String email;
}
