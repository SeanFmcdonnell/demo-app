package com.finicity.example.resources;

import com.finicity.client.FinicityClient;
import com.finicity.client.Subscriptions;
import com.finicity.client.models.Event;
import com.finicity.example.api.User;
import com.finicity.example.services.AuthService;
import com.google.api.client.util.Maps;
import io.dropwizard.auth.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

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
@AllArgsConstructor
@Path("subscriptions")
public class SubscriptionResource {
    private final FinicityClient client;
    private final AuthService auth;
    private final Map<String, EventOutput> outputs = Maps.newHashMap();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Subscriptions createSubscription(@Auth User user, String accountId, @Context UriInfo info) {
        URI callback = info.getRequestUriBuilder()
                .path("callback")
                .build();
        return client.createSubscription(user.getFinicityId(), accountId, callback.toASCIIString());
    }

    @POST
    @Path("callback")
    @Consumes(MediaType.APPLICATION_XML)
    public void callback(Event event) {
        event.getTransaction().stream()
                .forEach(transaction -> Optional.ofNullable(outputs.get(transaction.getCustomerId()))
                        .ifPresent(output -> {
                            try {
                                output.write(new OutboundEvent.Builder()
                                        .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                        .name("transaction")
                                        .data(event)
                                        .build());
                            } catch (IOException e) {
                                log.error("Error sending event", e);
                            }
                        }));
    }

    @GET
    @Path("events")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getServerSentEvents(@QueryParam("token") String token) {
        final EventOutput eventOutput = new EventOutput();
        User user = auth.getUser(token);
        outputs.put(user.getFinicityId(), eventOutput);
        return eventOutput;
    }

    @DELETE
    @Path("{id}")
    public void delete(@Auth User user, @PathParam("id") String id) {
        client.deleteSubscription(user.getFinicityId(), id);
    }
}
