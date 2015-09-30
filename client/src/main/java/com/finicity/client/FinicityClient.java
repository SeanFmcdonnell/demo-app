package com.finicity.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.finicity.client.models.*;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.beans.ConstructorProperties;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Created by jhutchins on 9/23/15.
 */
@Slf4j
public class FinicityClient {
    private static final Duration TOKEN_LIFE = Duration.ofMinutes(90);

    private final Credentials creds;
    private final String finicityAppKey;
    private final WebTarget target;
    private final XmlMapper xmlMapper = new XmlMapper();

    private String token = null;
    private Instant tokenExpiration = Instant.now();

    @ConstructorProperties({"partnerId", "partnerSecret", "finicityAppKey", "client"})
    private FinicityClient(String partnerId, String partnerSecret, String finicityAppKey, Client client) {
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
        this.tokenExpiration = Instant.now().plus(TOKEN_LIFE);
    }

    private void validateToken() {
        if (Instant.now().isAfter(this.tokenExpiration)) {
            getToken();
        }
    }

    public Institutions getInstitutions(String text, int start, int limit) {
        validateToken();
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

    public LoginForm getInstitutionLogin(int institutionId) {
        validateToken();
        return target.path("/v1/institutions/" + institutionId + "/loginForm")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .get(LoginForm.class);
    }

    public ActivationResponse addAllAccounts(String customerId, int institutionId, List<LoginField> fields) {
        validateToken();
        AccountCredentials account = new AccountCredentials();
        account.setList(fields);
        String str;
        try {
            str = xmlMapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        final Response response = target.path("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/addall")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .post(Entity.entity(str, MediaType.APPLICATION_XML_TYPE));
        return extractResponse(response);
    }

    public ActivationResponse addAllAccounts(String customerId, int institutionId, MfaChallenges challenges, String session) {
        validateToken();
        String str;
        try {
            str = xmlMapper.writeValueAsString(new MfaResponse(challenges));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        final Response response = target.path("/v1/customers/" + customerId + "/institutions/" + institutionId + "/accounts/addall/mfa")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .header("MFA-Session", session)
                .post(Entity.entity(str, MediaType.APPLICATION_XML_TYPE));
        return extractResponse(response);
    }

    private ActivationResponse extractResponse(Response response) {
        final String xml = response.readEntity(String.class);
        ActivationResponseBody body;
        try {
            body = xmlMapper.readValue(xml, Accounts.class);
        } catch (Exception e) {
            log.debug("Error mapping to Accounts object", e);
            try {
                body = xmlMapper.readValue(xml, MfaChallenges.class);
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        }
        ActivationResponse.Builder builder = ActivationResponse.builder()
                .body(body);
        if (response.getStatus() == 203) {
            builder.mfaSession(response.getHeaderString("MFA-Session"));
        }
        return builder.build();
    }

    public Accounts getCustomerAccounts(String customerId) {
        validateToken();
        return target.path("/v1/customers/" + customerId + "/accounts/")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .get(Accounts.class);
    }

    public Customers getCustomers(String search, int start, int limit) {
        validateToken();
        return target.path("/v1/customers")
                .queryParam("search", search)
                .queryParam("start", start)
                .queryParam("limit", limit)
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .get(Customers.class);
    }

    public Customer createTestCustomer(String username, String firstName, String lastName) {
       validateToken();
        final Customer customer = new Customer();
        customer.setUsername(username);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return target.path("/v1/customers/testing")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .post(Entity.entity(customer, MediaType.APPLICATION_XML_TYPE), Customer.class);
    }

    public Transactions getTransactions(String customerId, String accountId) {
        validateToken();
        return target.path("/v2/customers/" + customerId + "/accounts/" + accountId + "/transactions")
                .queryParam("fromDate", Instant.now().minus(Duration.ofDays(30)).getEpochSecond())
                .queryParam("toDate", Instant.now().getEpochSecond())
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .get(Transactions.class);
    }

    public Subscriptions createSubscription(String customerId, String accountId, String callback) {
        validateToken();
        Subscription subscription = new Subscription();
        subscription.setCallbackUrl(callback);
        return target.path("/v1/customers/" + customerId + "/accounts/" + accountId + "/txpush")
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .post(Entity.entity(subscription, MediaType.APPLICATION_XML_TYPE), Subscriptions.class);
    }

    public void deleteSubscription(String customerId, String subscriptionId) {
        validateToken();
        target.path("/v1/customers/" + customerId + "/subscriptions/" + subscriptionId)
                .request(MediaType.APPLICATION_XML_TYPE)
                .header("Finicity-App-Key", finicityAppKey)
                .header("Finicity-App-Token", token)
                .delete();
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
