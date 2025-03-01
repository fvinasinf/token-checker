package com.token.checker.token.checker.security.imp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.token.checker.token.checker.security.SecurityCheckerService;
import com.token.checker.token.checker.security.beans.Claims;
import com.token.checker.token.checker.security.beans.Header;

@Service
public class SecurityCheckerServiceImpl implements SecurityCheckerService {
    
    private static final String ALG = "HS512";
    private static final String REGEX_DOT = "\\.";
    private static final String MILLIS_CODA = "000";
    private static final String TIMEZONE = "UTC";

    @Value("${token.user.for.provider}")
    private String tokenEmitter;

    private ObjectMapper jsonReader;

    public SecurityCheckerServiceImpl(ObjectMapper jsonReader) {
        this.jsonReader = jsonReader;
    }

    @Override
    public boolean validateAlloweness(String token) {
        return token != null && !token.isEmpty();
    }

    @Override
    public boolean validateStructureAndTime(String token) {
        String [] tokenElements = token.split(REGEX_DOT);

        Header head = readHeader(tokenElements[0]);
        Claims claims = readClaims(tokenElements[1]);

        return 
            tokenElements.length == 3
            && checkHeader(head)
            && checkClaims(claims);
    }

    private Header readHeader(String headerElement) {
        try {
            String decoded = new String(Base64.getDecoder().decode(headerElement));
            return jsonReader.readValue(decoded, Header.class);
        } catch (JsonProcessingException e) {
            return new Header();
        }
    }

    private Claims readClaims(String claimsElement) {
        try {
            String decoded = new String(Base64.getDecoder().decode(claimsElement));
            return jsonReader.readValue(decoded, Claims.class);
        } catch (JsonProcessingException e) {
            return new Claims();
        }
    }

    private Boolean checkHeader(Header header) {
        return 
            header.getAlg() != null
            && header.getAlg().compareTo(ALG) == 0; 
    }

    private Boolean checkClaims(Claims claim) {
        return 
            claim.getSub() != null
            && claim.getSub().compareTo(tokenEmitter) == 0
            && claim.getExp() != null
            && checkExpValidity(claim.getExp())
            && claim.getIat() != null; 
    }

    private Boolean checkExpValidity(Long exp) {
        String expToMillis = exp.toString() + MILLIS_CODA;
        Long expMillis = Long.valueOf(expToMillis);
        LocalDateTime expDate = 
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(expMillis.longValue()), 
                TimeZone.getTimeZone(TIMEZONE).toZoneId()
            );
        return expDate.isAfter(LocalDateTime.now());
    }

}
