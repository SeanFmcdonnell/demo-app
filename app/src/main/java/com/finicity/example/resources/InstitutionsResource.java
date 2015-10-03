package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.*;
import com.finicity.example.api.User;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * Created by jhutchins on 9/26/15.
 */
@Slf4j
@AllArgsConstructor
@Path("api/institutions")
public class InstitutionsResource {

    private final FinicityClient client;

    @GET
    public Institutions getInstitution(
            @QueryParam("search") final String search,
            @QueryParam("page") @DefaultValue("1") final int page) {
        return client.getInstitutions(search, page, 10);
    }

    @GET
    @Path("{id}/login")
    public LoginForm getLogin(@PathParam("id") final int id) {
        return client.getInstitutionLogin(id);
    }

    @POST
    @Path("{type}/{id}/discover")
    @Consumes(MediaType.APPLICATION_JSON)
    public ActivationResponseBody discover(
            @Auth final User user,
            @PathParam("type") final String type,
            @PathParam("id") final int id,
            final LoginForm form) {
        ActivationResponse response = client.addAllAccounts(user.getId(type), id, form.getFields());
        user.setCurrentMfa(response.getMfaSession());
        return response.getBody();
    }

    @POST
    @Path("{type}/{id}/discover/mfa")
    @Consumes(MediaType.APPLICATION_JSON)
    public ActivationResponseBody mfa(
            @Auth final User user,
            @PathParam("type") final String type,
            @PathParam("id") final int id,
            final MfaChallenges challenges) {
        ActivationResponse response = client.addAllAccounts(user.getId(type), id, challenges, user.getCurrentMfa());
        user.setCurrentMfa(response.getMfaSession());
        return response.getBody();
    }
}
