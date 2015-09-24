package com.finicity.client.models;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

/**
 * Created by jhutchins on 9/24/15.
 */
public class InstitutionTest {
    private static final XmlMapper MAPPER = new XmlMapper();

    @Test
    public void deserializeFromXML() throws Exception {
        final Institution institution = new Institution();
        institution.setId(1097);
        institution.setName("Bank & Trust Co, IL");
        institution.setAccountTypeDescription("Banking");
        institution.setUrlHomeApp("http://www.bank-and-trust.com/");
        institution.setUrlLogonApp("https://www.bank-and-trusthb.com/onlineserv/HB/Signon.cgi");
        assertThat(MAPPER.readValue(fixture("fixtures/institution.xml"), Institution.class))
                .isEqualToComparingFieldByField(institution);
    }
}
