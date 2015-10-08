package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.finicity.client.FinicityClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by ckuhn on 10/8/15.
 */
@Slf4j
@Data
@JacksonXmlRootElement(localName = "event")
public class AccountEvent extends Event {
    @JacksonXmlElementWrapper(localName = "records")
    private List<Account> accounts;

    @Override
    public void send(Map<String, EventOutput> outputs, FinicityClient client) {
        accounts.stream()
                .filter(account -> outputs.get(account.getCustomerId()) != null)
                .map(account -> {
                    EventOutput output = outputs.get(account.getCustomerId());
                    try {
                        output.write(new OutboundEvent.Builder()
                                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                .name("account")
                                .data(account)
                                .build());
                        log.info("Wrote account [{}]", account.toString());
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
}
