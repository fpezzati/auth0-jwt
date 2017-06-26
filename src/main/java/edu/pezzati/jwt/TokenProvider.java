package edu.pezzati.jwt;

public interface TokenProvider {

    String getToken(JwtToken token) throws Exception;
}
