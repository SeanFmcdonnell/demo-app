package com.finicity.example.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * Created by jhutchins on 9/25/15.
 */
@Getter
@Builder
public class User {
    @NotEmpty
    private final String activeId;
    @NotEmpty
    private final String testingId;
    @NotEmpty
    private final String googleId;
    private final String email;
    @Setter
    private String currentMfa;

    public String getId(String type) {
        return (type.equals("active")) ? activeId : testingId;
    }
}
