package edu.pezzati.jwt;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@RunWith(WeldTest.class)
public class JwtTokenProviderTest {

    private static final String passphrase = "supersecretpassphrase";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Inject
    private TokenProvider tokenProvider;
    /**
     * The following token was created in jwt.io by this structure: { "alg":
     * "HS256", "typ": "JWT" } { "sub": "dummySubject", "exp": "1498054620653" }
     */
    private String expectedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkdW1teVN1YmplY3QiLCJleHAiOiIxNDk4MDU0NjIwNjUzIn0.VTL9gIQ-POIoI8W0OI9y_Q7rDGFbniUGNcfd_EliK60";
    private String preconfiguredSubject;
    private ZonedDateTime preconfiguredExpirationTime;

    @Before
    public void setUp() {
	tokenProvider = new JwtTokenProvider();
	MockitoAnnotations.initMocks(this);
	preconfiguredSubject = "dummySubject";
	Instant istant = Instant.ofEpochMilli(1498054620653L);
	preconfiguredExpirationTime = ZonedDateTime.ofInstant(istant, ZoneId.of("Europe/Rome"));
    }

    @Test
    public void returnTokenByValidPayload() throws Exception {
	JwtToken token = new JwtToken();
	token.setSubject(preconfiguredSubject);
	token.setExpirationTime(preconfiguredExpirationTime);
	((JwtTokenProvider) tokenProvider).setPassphrase(passphrase);
	String actualToken = tokenProvider.getToken(token);
	Assert.assertEquals(expectedToken, actualToken);
    }
}
