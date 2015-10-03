package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.Accounts;
import com.finicity.client.models.Transactions;
import com.finicity.example.api.User;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;

/**
 * Created by jhutchins on 9/28/15.
 */
@Path("api/accounts")
@AllArgsConstructor
public class AccountsResource {
    private final FinicityClient client;

    @GET
    @Path("{type}")
    public Accounts getAccounts(@Auth User user, @PathParam("type") final String type) {
        return client.getCustomerAccounts(user.getId(type));
    }

    @POST
    @Path("{type}/refresh")
    public Accounts refresh(@Auth User user, @PathParam("type") final String type) {
        return client.refreshAccounts(user.getId(type));
    }

    @GET
    @Path("{type}/{id}/transactions")
    public Transactions getTransactions(
            @Auth User user,
            @PathParam("id") String id,
            @PathParam("type") final String type) {
        return client.getTransactions(user.getId(type), id);
    }
}
