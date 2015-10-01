package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.Accounts;
import com.finicity.client.models.Transactions;
import com.finicity.example.api.User;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by jhutchins on 9/28/15.
 */
@Path("api/accounts")
@AllArgsConstructor
public class AccountsResource {
    private final FinicityClient client;

    @GET
    public Accounts getAccounts(@Auth User user) {
        return client.getCustomerAccounts(user.getFinicityId());
    }

    @POST
    @Path("refresh")
    public Accounts refresh(@Auth User user) {
        return client.refreshAccounts(user.getFinicityId());
    }

    @GET
    @Path("{id}/transactions")
    public Transactions getTransactions(@Auth User user, @PathParam("id") String id) {
        return client.getTransactions(user.getFinicityId(), id);
    }
}
