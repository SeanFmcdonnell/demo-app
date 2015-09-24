package com.finicity.example;

import com.finicity.client.FinicityClient;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Client;

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
        JerseyClientConfiguration jerseyConfig = config.getJerseyClientConfiguration();
        jerseyConfig.setGzipEnabled(false);
        final Client client = new JerseyClientBuilder(env)
                .using(jerseyConfig)
                .build(getName());
        final FinicityClient finicity = config.getFinicityClient().build(env, client);
        Object o = finicity.getInstitutions("bank", 1, 10);
        System.out.println(o);
    }
}
