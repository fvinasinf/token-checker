package com.token.checker.token.checker.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.token.checker.token.checker.beans.TokenProviderRequest;
import com.token.checker.token.checker.beans.TokenProviderResponse;

@FeignClient(value="tokenProvider", url="${token.provider}")
public interface TokenProviderClient {

    /**
     * Takes the token {"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXR..."} from the provider
     * @param request user and password to talk with the provider
     * @return token from provider
     */
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenProviderResponse getTokenFromClient(TokenProviderRequest request);

}
