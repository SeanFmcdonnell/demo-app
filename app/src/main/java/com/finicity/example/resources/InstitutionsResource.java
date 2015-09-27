package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.Accounts;
import com.finicity.client.models.Institutions;
import com.finicity.client.models.LoginField;
import com.finicity.client.models.LoginForm;
import com.finicity.example.api.User;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public Accounts discover(
            @PathParam("id") final int id,
            @Auth final User user,
            final LoginForm form) {
        return client.discoverAccounts(user.getFinicityId(), id, form.getFields());
    }

    @POST
    @Path("{id}/activate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Accounts activate(
            @PathParam("id") final int id,
            @Auth final User user,
            final Accounts accounts) {
        return client.activateAccounts(user.getFinicityId(), id, accounts);
    }

}
