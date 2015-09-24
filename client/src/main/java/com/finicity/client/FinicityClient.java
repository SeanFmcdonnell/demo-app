package com.finicity.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.finicity.client.models.Access;
import com.finicity.client.models.Credentials;
import com.finicity.client.models.Institutions;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.beans.ConstructorProperties;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Created by jhutchins on 9/23/15.
 */
public class FinicityClient {
    private final Credentials creds;
    private final String finicityAppKey;
    private final WebTarget target;

    private String token = null;

    @ConstructorProperties({"partnerId", "partnerSecret", "finicityAppKey", "client"})
    private FinicityClient(String partnerId, String partnerSecret, String finicityAppKey, Client client) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.findAndRegisterModules();
        JacksonJaxbXMLProvider provider = new JacksonJaxbXMLProvider();
        provider.setMapper(xmlMapper);

        this.creds = Credentials.builder()
                .partnerId(partnerId)
                .partnerSecret(partnerSecret)
                .build();
        this.finicityAppKey = finicityAppKey;
        this.target = client
                .register(provider)
                .target("https://api.finicity.com/aggregation");
    }

    public static FinicityClientBuilder builder() {
        return new FinicityClientBuilder();
    }

    private void getToken() {
        Access access = target.path("v2/partners/authentication")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .post(Entity.entity(creds, MediaType.APPLICATION_XML_TYPE), Access.class);
        this.token = access.getToken();
    }

    public Institutions getInstitutions(String text, int start, int limit) {
        if (token == null) {
            getToken();
        }
        text = text.replace(' ', '+');
        return target.path("v1/institutions")
                .queryParam("search", text)
                .queryParam("start", start)
                .queryParam("limit", limit)
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .get(Institutions.class);
    }

    @Setter
    @ToString
    @Accessors(fluent = true)
    public static class FinicityClientBuilder {
        private String partnerId;
        private String partnerSecret;
        private String finicityAppKey;
        private Client client;

        public FinicityClient build() {
            return new FinicityClient(partnerId, partnerSecret, finicityAppKey, client);
        }
    }
}
