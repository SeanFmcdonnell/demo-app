package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.*;
import com.finicity.example.api.User;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by jhutchins on 9/26/15.
 */
@AllArgsConstructor
@Path("institutions")
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
    @Path("{id}/discover")
    @Consumes(MediaType.APPLICATION_JSON)
    public ActivationResponse discover(
            @PathParam("id") final int id,
            @Auth final User user,
            final LoginForm form) {
        return client.addAllAccounts(user.getFinicityId(), id, form.getFields());
    }
}
