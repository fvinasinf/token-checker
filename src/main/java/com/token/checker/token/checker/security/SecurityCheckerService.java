package com.token.checker.token.checker.security;

public interface SecurityCheckerService {
    /**
     * Checks the token to see if it should be forbidden or not, if it is void or if it is null
     * @param token The token to check
     * @return Token allowness
     */
    public boolean validateAlloweness(String token);
    /**
     * Validates the structure of the token and checks if the token is not expired
     * @param token The token to check
     * @return Token validation
     */
    public boolean validateStructureAndTime(String token);
}
