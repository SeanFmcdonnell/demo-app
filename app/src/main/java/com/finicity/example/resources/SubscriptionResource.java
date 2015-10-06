package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.models.Subscriptions;
import com.finicity.client.models.Event;
import com.finicity.client.models.Transaction;
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
import java.util.Map;
import java.util.Optional;

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
        URI callback = info.getRequestUriBuilder()
                .path("callback")
                .build();
        return client.createSubscription(user.getId(type), accountId, callback.toASCIIString());
    }

    @POST
    @Path("callback")
    @Consumes(MediaType.APPLICATION_XML)
    public void callback(Event event) {

        event.getTransaction().stream()
                .filter(transaction -> outputs.get(transaction.getCustomerId()) != null)
                .map(transaction -> {
                    log.info(transaction.toString());
                    EventOutput output = outputs.get(transaction.getCustomerId());
                    try {
                        output.write(new OutboundEvent.Builder()
                                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                .name("transaction")
                                .data(transaction)
                                .build());
                        log.info("Wrote transaction [{}]", transaction);
                    } catch (IOException e) {
                        log.error("Error sending event", e);
                    }
                    return output;
                })
                .distinct()
                .forEach(output -> {
                    try {
                        output.close();
                    } catch (IOException e) {
                        log.error("Failed to close connection", e);
                    }
                });
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getServerSentEvents(@QueryParam("token") String token) {
        final EventOutput eventOutput = new EventOutput();
        User user = auth.getUser(token);
        Optional.ofNullable(outputs.get(user.getId("active"))).ifPresent(output -> {
            try {
                output.close();
            } catch (IOException e) {
                log.error("Error closing output", e);
            }
        });
        outputs.put(user.getId("active"), eventOutput);
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
