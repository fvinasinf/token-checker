package com.token.checker.token.checker.services;

public interface TokenService {
    /**
     * Gets the token from the client
     * @reutn token
     */
    String getToken();
    /**
     * Gets the now timestamp in ISO 8601 format
     * @return now's timestamp in the format
     */
    String getTimestamp();
}
