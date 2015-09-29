package com.finicity.example.services;

import com.finicity.example.api.User;
import com.google.api.client.util.Maps;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Map;
import java.util.UUID;

/**
 * Created by jhutchins on 9/25/15.
 */
public class AuthService implements Authenticator<String, User> {

    private final Map<String, User> users = Maps.newHashMap();

    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        return Optional.fromNullable(users.get(token));
    }

    public User getUser(String token) {
        return users.get(token);
    }

    public String registerUser(User user) {
        String token = UUID.randomUUID().toString();
        users.put(token, user);
        return token;
    }
}
