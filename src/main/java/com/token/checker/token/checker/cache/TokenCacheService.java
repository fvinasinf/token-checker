package com.token.checker.token.checker.cache;

public interface TokenCacheService {
    /**
     * Gets the cached token from RAM if it is valid
     * @return token
     */
    public String getCachedTokenIfValid();
    /**
     * Caches the token in RAM
     * @param token cached
     */
    public void cacheToken(String token);
}
