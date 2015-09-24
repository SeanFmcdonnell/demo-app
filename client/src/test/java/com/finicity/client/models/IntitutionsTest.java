package com.finicity.client.models;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jhutchins on 9/24/15.
 */
public class IntitutionsTest {
    private static final XmlMapper MAPPER = new XmlMapper();

    @Test
    public void deserializeFromXML() throws Exception {

        final Institution institution1 = new Institution();
        institution1.setId(1097);
        institution1.setName("Bank & Trust Co, IL");
        institution1.setAccountTypeDescription("Banking");
        institution1.setUrlHomeApp("http://www.bank-and-trust.com/");
        institution1.setUrlLogonApp("https://www.bank-and-trusthb.com/onlineserv/HB/Signon.cgi");

        final Institutions institutions = new Institutions();
        institutions.setFound(7079);
        institutions.setDisplaying(1);
        institutions.setMoreAvailable(true);
        institutions.setCreatedDate("1443126591");
        institutions.setList(Lists.newArrayList(institution1));

        assertThat(MAPPER.readValue(fixture("fixtures/institutions.xml"), Institutions.class))
                .isEqualToComparingFieldByField(institutions);
    }
}
