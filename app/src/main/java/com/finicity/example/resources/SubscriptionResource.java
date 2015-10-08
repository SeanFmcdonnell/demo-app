package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.*;
import com.finicity.example.api.User;
import com.finicity.example.services.AuthService;
import com.google.api.client.util.Maps;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Created by jhutchins on 9/29/15.
 */
@Slf4j
@Singleton
@AllArgsConstructor
@Path("api/subscriptions")
public class SubscriptionResource {
    private final FinicityClient client;
    private final AuthService auth;
    private final Map<String, EventOutput> outputs = Maps.newHashMap();

    @POST
    @Path("{type}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Subscriptions createSubscription(
            @Auth User user,
            @PathParam("type") final String type,
            String accountId,
            @Context UriInfo info) {
        URI callback = info.getBaseUriBuilder()
                .path("api/subscriptions/callback")
                .build();
        return client.createSubscription(user.getId(type), accountId, callback.toASCIIString());
    }

    @POST
    @Path("callback")
    @Consumes(MediaType.APPLICATION_XML)
    public void callback(Event event) {
        event.send(outputs, client);
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getServerSentEvents(@QueryParam("token") String token) {
        final User user = auth.getUser(token);
        final EventOutput eventOutput = new EventOutput();

        for (String type : new String[]{"active", "testing"}) {
            final String id = user.getId(type);
            Optional.ofNullable(outputs.get(id)).ifPresent(output -> {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("Error closing output", e);
                }
            });
            outputs.put(id, eventOutput);
        }
        return eventOutput;
    }

    @DELETE
    @Path("{type}/{id}")
    public void delete(
            @Auth User user,
            @PathParam("type") final String type,
            @PathParam("id") String id) {
        client.deleteSubscription(user.getId("type"), id);
    }
}
