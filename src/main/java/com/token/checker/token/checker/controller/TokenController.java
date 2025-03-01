package com.token.checker.token.checker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.token.checker.token.checker.beans.TokenResponse;
import com.token.checker.token.checker.cache.TokenCacheService;
import com.token.checker.token.checker.logging.LoggingService;
import com.token.checker.token.checker.metrics.MetricsService;
import com.token.checker.token.checker.security.SecurityCheckerService;
import com.token.checker.token.checker.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

/**
 * Get token reads the token from the external provider through a Feign Client.
 * With Feign client we can easely call an external method through an interface
 * without explicitly implementing the client internals. Then the date is generated 
 * by Java traditional methods transforming it to its proper format (ISO 8601).
 * Then we validate the token checking the structure (analized with json.io), and
 * with Jackson we check the structure and the integrity of the values inside.
 * To check the time validity, we will use the exp field, and calculate the validity
 * against the now date. If token is null or "", the authentication is FORBIDDEN. If
 * token is expired or structure is bad formed, then the authentication is UNAUTHORIZED.
 * Token persistence in RAM is done with one string variable encapsulated on a service, 
 * if this token is invalid, is overwritten by a new token. There are no more users of the
 * external provider than one (the microservice), so we can mantain only one token.
 * As far as we don't know the secret, we can't validate the sign of the token, so we won't 
 * put it on the basic validation. We also check on tokens that token emitter is 'auth-vivelibre'.
 * A logging service have been implemented, with custom file generation for simplicity to be exploited 
 * by external apps like Graphana.
 */
@Tag(name="Token", description = "Token consumer endpoint for getting token from external provider and validator")
@RestController()
@RequestMapping("${token.path}")
@Log4j2
public class TokenController {

    private static final String VALID_ACCESS_TOKEN = "Valid access, token: %s";
    private static final String UNAUTORIZED_ACCESS_TOKEN = "Unautorized access, token: %s";
    private static final String FORBIDDEN_ACCESS = "Forbidden access";
    private TokenService tokenService;
    private SecurityCheckerService securityCheckerService;
    private TokenCacheService tokenCacheService;
    private MetricsService metricsService;
    private LoggingService loggingService;

    public TokenController(TokenService tokenService, SecurityCheckerService securityCheckerService, TokenCacheService tokenCacheService, MetricsService metricsService, LoggingService loggingService) {
        this.tokenService = tokenService;
        this.securityCheckerService = securityCheckerService;
        this.tokenCacheService = tokenCacheService;
        this.metricsService = metricsService;
        this.loggingService= loggingService;
    }
    
    @Operation(summary = "Get token", description = "Gets the token and the timestamp, and validates the structure and validity of the token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token acquired"),
        @ApiResponse(responseCode = "403", description = "Forbidden access"),
        @ApiResponse(responseCode = "401", description = "Unauthorized access")
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> getToken() {
        metricsService.pettitionProcessed();

        String token = tokenCacheService.getCachedTokenIfValid();
        String timestamp = tokenService.getTimestamp();
        
        if (token.compareTo("") == 0) {
            token = tokenService.getToken();
            tokenCacheService.cacheToken(token);
        }
        
        if (!securityCheckerService.validateAlloweness(token)) {
            loggingService.error(FORBIDDEN_ACCESS);
            return new ResponseEntity<TokenResponse>(HttpStatus.FORBIDDEN);
        }
        if (!securityCheckerService.validateStructureAndTime(token)) {
            loggingService.error(String.format(UNAUTORIZED_ACCESS_TOKEN, token));
            return new ResponseEntity<TokenResponse>(HttpStatus.UNAUTHORIZED);
        }
        
        TokenResponse response = new TokenResponse();
        response.setToken(token);
        response.setTimestamp(timestamp);

        loggingService.info(String.format(VALID_ACCESS_TOKEN, token));
        
        return ResponseEntity.ok(response);
    }

}
