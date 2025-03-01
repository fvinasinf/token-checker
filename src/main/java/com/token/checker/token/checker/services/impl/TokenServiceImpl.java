package com.token.checker.token.checker.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.token.checker.token.checker.beans.TokenProviderRequest;
import com.token.checker.token.checker.beans.TokenProviderResponse;
import com.token.checker.token.checker.clients.TokenProviderClient;
import com.token.checker.token.checker.services.DateFormatterService;
import com.token.checker.token.checker.services.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.user.for.provider}")
    private String user;
    @Value("${token.password.for.provider}")
    private String password;

    private TokenProviderClient tokenRefactorService;
    private DateFormatterService dateFormatterService;

    public TokenServiceImpl(TokenProviderClient client, DateFormatterService dateFormatterService) {
        this.tokenRefactorService = client;
        this.dateFormatterService = dateFormatterService;
    }

    @Override
    public String getToken() {
        /*Feign client for connection with external resource, preparing the credentials*/
        TokenProviderRequest requestToClient = new TokenProviderRequest();
        requestToClient.setUsername(user);
        requestToClient.setPassword(password);
        
        /* Communicating with the external resource to get the token */
        TokenProviderResponse responseFromClient = tokenRefactorService.getTokenFromClient(requestToClient);
        return responseFromClient.getToken();
    }

    @Override
    public String getTimestamp() {
        return dateFormatterService.nowFormmattedTimestamp();
    }
    
}


