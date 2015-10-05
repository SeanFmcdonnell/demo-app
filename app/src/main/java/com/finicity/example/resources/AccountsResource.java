package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.Account;
import com.finicity.client.models.Accounts;
import com.finicity.client.models.Transactions;
import com.finicity.example.api.User;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by jhutchins on 9/28/15.
 */
@Slf4j
@Path("api/accounts")
@AllArgsConstructor
public class AccountsResource {
    private final FinicityClient client;

    @GET
    @Path("{type}")
    public Accounts getAccounts(@Auth User user, @PathParam("type") final String type) {
        Accounts accounts = client.getCustomerAccounts(user.getId(type));
        return accounts;
    }

    @DELETE
    @Path("{type}/{id}")
    public Accounts deleteAccounts(
            @Auth User user,
            @PathParam("id") String id,
            @PathParam("type") final String type) {
        Response resp = client.deleteAccount(user.getId(type), id);
        if (resp.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            return null;
        }
        return client.getCustomerAccounts(user.getId(type));
    }

    ;

    @POST
    @Path("{type}/refresh")
    public Accounts refresh(@Auth User user, @PathParam("type") final String type) {
        return client.refreshAccounts(user.getId(type));
    }

    @POST
    @Path("{type}/refresh/{id}")
    public Account refresh(
            @Auth User user,
            @PathParam("id") String id,
            @PathParam("type") final String type) {
        return client.refreshAccount(user.getId(type), id);
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
