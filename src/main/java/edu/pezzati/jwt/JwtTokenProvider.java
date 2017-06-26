package edu.pezzati.jwt;

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtTokenProvider implements TokenProvider {

    private Validator validator;
    private String passphrase;

    public JwtTokenProvider() {
	validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public String getToken(JwtToken token) throws Exception {
	validate(token);
	return computeToken(token);
    }

    private String computeToken(JwtToken token) throws Exception {
	Algorithm algorithmHS = Algorithm.HMAC256(passphrase);
	Date expirationTime = new Date(token.getExpirationTime().toInstant().toEpochMilli());
	Builder jwtToken = JWT.create().withSubject(token.getSubject()).withExpiresAt(expirationTime);
	Iterator<Entry<String, Object>> candidateClaimsIterator = token.getPayload().entrySet().iterator();
	while (candidateClaimsIterator.hasNext()) {
	    Entry<String, Object> candidateClaim = candidateClaimsIterator.next();
	    if (candidateClaim.getValue() instanceof Boolean) {
		jwtToken = jwtToken.withClaim(candidateClaim.getKey(), (Boolean) candidateClaim.getValue());
	    } else if (candidateClaim.getValue() instanceof Date) {
		jwtToken = jwtToken.withClaim(candidateClaim.getKey(), (Date) candidateClaim.getValue());
	    } else if (candidateClaim.getValue() instanceof Double) {
		jwtToken = jwtToken.withClaim(candidateClaim.getKey(), (Double) candidateClaim.getValue());
	    } else if (candidateClaim.getValue() instanceof Integer) {
		jwtToken = jwtToken.withClaim(candidateClaim.getKey(), (Integer) candidateClaim.getValue());
	    } else if (candidateClaim.getValue() instanceof Long) {
		jwtToken = jwtToken.withClaim(candidateClaim.getKey(), (Long) candidateClaim.getValue());
	    } else {
		jwtToken = jwtToken.withClaim(candidateClaim.getKey(), (String) candidateClaim.getValue());
	    }
	}
	return jwtToken.sign(algorithmHS);
    }

    private void validate(JwtToken token) throws Exception {
	Set<ConstraintViolation<JwtToken>> violatedConstraints = validator.validate(token);
	if (!violatedConstraints.isEmpty()) {
	    StringBuilder sb = new StringBuilder();
	    Iterator<ConstraintViolation<JwtToken>> violationIterator = violatedConstraints.iterator();
	    while (violationIterator.hasNext()) {
		ConstraintViolation<JwtToken> violation = violationIterator.next();
		sb.append(buildErrorMessage(violation));
		sb.append("\n");
	    }
	    throw new Exception(sb.toString());
	}
    }

    private String buildErrorMessage(ConstraintViolation<JwtToken> violation) {
	StringBuffer sb = new StringBuffer();
	sb.append(violation.getMessage());
	return sb.toString();
    }

    public void setPassphrase(String passphrase) {
	this.passphrase = passphrase;
    }
}
