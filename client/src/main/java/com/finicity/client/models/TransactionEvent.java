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
public class TransactionEvent extends Event {
    @JacksonXmlElementWrapper(localName = "records")
    private List<Transaction> transactions;

    @Override
    public void send(Map<String, EventOutput> outputs, FinicityClient client) {
        transactions.stream()
                .filter(transaction -> outputs.get(transaction.getCustomerId()) != null)
                .map(transaction -> {
                    EventOutput output = outputs.get(transaction.getCustomerId());
                    List<Account> accounts = client.getCustomerAccounts(transaction.getCustomerId()).getList();
                    accounts.stream()
                            .filter(account -> account.getId().equals(transaction.getAccountId()))
                            .map(account -> {
                                transaction.setAccountName(account.getName());
                                return transaction;
                            })
                            .distinct()
                            .forEach(t -> {
                                try {
                                    output.write(new OutboundEvent.Builder()
                                            .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                            .name("transaction")
                                            .data(t)
                                            .build());
                                    log.info("Wrote transaction [{}]", t.toString());
                                } catch (IOException e) {
                                    log.error("Error sending event", e);
                                }

                            });
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
