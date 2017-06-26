package edu.pezzati.jwt;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class JwtToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "Subject can not be null")
    @Size(min = 10)
    private String subject;
    @NotNull(message = "Expiration time can not be null")
    private ZonedDateTime expirationTime;
    private Map<String, Object> payload;

    public JwtToken() {
	payload = new HashMap<>();
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public ZonedDateTime getExpirationTime() {
	return expirationTime;
    }

    public void setExpirationTime(ZonedDateTime expirationTime) {
	this.expirationTime = expirationTime;
    }

    public Map<String, Object> getPayload() {
	return payload;
    }

    public void setPayload(Map<String, Object> payload) {
	this.payload = payload;
    }

    @Override
    public int hashCode() {
	return Objects.hash(subject, expirationTime);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (!(obj instanceof JwtToken))
	    return false;
	JwtToken token = (JwtToken) obj;
	if (!Objects.equals(this.subject, token.subject))
	    return false;
	if (!Objects.equals(this.subject, token.expirationTime))
	    return false;
	if (!Objects.equals(this.subject, token.payload))
	    return false;
	return true;
    }
}
