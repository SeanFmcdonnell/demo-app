package com.finicity.client.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jhutchins on 9/26/15.
 */
public class LoginFormTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void deserializesFromJSON() throws Exception {
        final LoginField field1 = new LoginField();
        field1.setId(101732001);
        field1.setName("Banking Userid");
        field1.setValue("demo");
        field1.setDescription("Banking Userid");
        field1.setDisplayOrder(1);

        final LoginField field2= new LoginField();
        field2.setId(101732002);
        field2.setName("Banking Password");
        field2.setValue("go");
        field2.setDescription("Banking Password");
        field2.setDisplayOrder(2);
        field2.setMask(true);

        final LoginForm form = new LoginForm();
        form.setFields(Lists.newArrayList(field1, field2));

        assertThat(MAPPER.readValue(fixture("fixtures/loginForm.json"), LoginForm.class))
                .isEqualTo(form);
    }
}
