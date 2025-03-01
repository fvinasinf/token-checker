package com.token.checker.token.checker.cache.imp;

import org.springframework.stereotype.Service;

import com.token.checker.token.checker.cache.TokenCacheService;
import com.token.checker.token.checker.security.SecurityCheckerService;

@Service
public class TokenCacheServiceImpl implements TokenCacheService {

    private String tokenCache;

    private SecurityCheckerService securityCheckerService;

    public TokenCacheServiceImpl(SecurityCheckerService securityCheckerService) {
        this.securityCheckerService = securityCheckerService;
        this.tokenCache = "";
    }

    @Override
    public String getCachedTokenIfValid() {
        return
            !securityCheckerService.validateAlloweness(this.tokenCache) 
            || !securityCheckerService.validateStructureAndTime(this.tokenCache)
            ? ""
            : this.tokenCache;
    }

    @Override
    public void cacheToken(String token) {
        this.tokenCache = token;
    }

}
