package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.Customer;
import com.finicity.client.models.Customers;
import com.finicity.example.api.User;
import com.finicity.example.services.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jhutchins on 9/24/15.
 */
@Slf4j
@Builder
@Path("google")
public class GoogleResource {

    private final AuthService auth;
    private final GoogleIdTokenVerifier verifier;
    private final FinicityClient client;

    @POST
    @Path("signin")
    public String signin(String idTokenString) throws GeneralSecurityException, IOException {
//        String result = null;
//        GoogleIdToken idToken = verifier.verify(idTokenString);
//        if (idToken != null) {
//            final GoogleIdToken.Payload payload = idToken.getPayload();
//            log.info("User ID: {}", payload.getSubject());
//            final String finicityId = getFinicityId(payload.getSubject());
//            User user = User.builder()
//                    .finicityId(finicityId)
//                    .googleId(payload.getSubject())
//                    .email(payload.getEmail())
//                    .build();
//            result = auth.registerUser(user);
//        } else {
//            log.error("Invalid ID token {}", idTokenString);
//        }
//        return result;
        final String finicityId = getFinicityId(idTokenString);
        User user = User.builder()
                .finicityId(finicityId)
                .googleId(idTokenString)
                .build();
        return auth.registerUser(user);
    }

    private String getFinicityId(String googleId) {
        final Customers customers = client.getCustomers(googleId, 1, 1);
        List<Customer> list = customers.getList();
        if (list == null || list.size() == 0) {
           return client.createTestCustomer(googleId, "first", "last").getId();
        }
        else {
           return customers.getList().get(0).getId();
        }
    }
}
