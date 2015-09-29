package com.finicity.example;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

import com.finicity.client.FinicityClient;
import com.finicity.example.api.User;
import com.finicity.example.resources.AccountsResource;
import com.finicity.example.resources.InstitutionsResource;
import com.finicity.example.resources.SubscriptionResource;
import com.finicity.example.services.AuthService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;
import java.util.EnumSet;

/**
 * Created by jhutchins on 9/23/15.
 */
public class Example extends Application<ExampleConfiguration> {

    public static void main(String[] args) throws Exception {
        new Example().run(args);
    }

    @Override
    public String getName() {
        return "example";
    }

    @Override
    public void run(ExampleConfiguration config, Environment env) throws Exception {
        setupCors(env);
        JerseyClientConfiguration jerseyClientConfiguration = config.getJerseyClientConfiguration();
        jerseyClientConfiguration.setGzipEnabled(false);
        final AuthService authService = new AuthService();
        final Client client = new JerseyClientBuilder(env)
                .using(jerseyClientConfiguration)
                .build(getName());
        final FinicityClient finicity = config.getFinicityClient().build(env, client);
        env.jersey().register(config.getGoogleReource().build(authService, finicity));
        env.jersey().register(new InstitutionsResource(finicity));
        env.jersey().register(new AccountsResource(finicity));
        env.jersey().register(new SubscriptionResource(finicity, authService));
        env.jersey().register(AuthFactory.binder(new OAuthFactory<>(authService, "SUPER SECRET STUFF", User.class)));
    }

    private void setupCors(Environment env) {
        FilterRegistration.Dynamic filter = env.servlets().addFilter("CORSFilter", CrossOriginFilter.class);

        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, env.getApplicationContext().getContextPath() + "*");
        filter.setInitParameter(ALLOWED_METHODS_PARAM, "GET,PUT,POST,OPTIONS,DELETE");
        filter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
        filter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");
    }
}
