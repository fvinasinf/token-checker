package com.token.checker.token.checker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.token.checker.token.checker.beans.TokenProviderResponse;
import com.token.checker.token.checker.clients.TokenProviderClient;
import com.token.checker.token.checker.services.impl.DateFormatterServiceImpl;
import com.token.checker.token.checker.services.impl.TokenServiceImpl;

@SpringBootTest(
    properties = {
        "token.user.for.provider=auth-vivelibre",
        "token.password.for.provider=password"
    }
)
@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Mock
    private TokenProviderClient client;

    @Mock
    private DateFormatterServiceImpl dateFormatterService;

    @Value("${token.user.for.provider}")
    private String user;
    
    @Value("${token.password.for.provider}")
    private String password;

    @Test
    public void getToken() {
        String mockToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoLXZpdmVsaWJyZSIsImV4cCI6MTc0MDg2NzE1MywiaWF0IjoxNzQwODQ5MTUzfQ.pX0NM-1ElbZ02h2UsMGTjTRVedI9FxQ0RZPXqwK5n3rS36EwliXBNbwzEU_szLoXoS6LyN6h3YfgJMotGI3-qg";

        TokenProviderResponse tokenProviderResponseMock = new TokenProviderResponse();
        tokenProviderResponseMock.setToken(mockToken);
        
        when(client.getTokenFromClient(any())).thenReturn(tokenProviderResponseMock);

        String token = tokenService.getToken();

        assertEquals(mockToken, token, "Tokens not equal taken from server");
    }

    @Test
    public void getTimestamp() {
        String mockedFormatted = "2025-03-01T18:40:26+0000";
        when(dateFormatterService.nowFormmattedTimestamp()).thenReturn(mockedFormatted);

        String formatted = tokenService.getTimestamp();

        assertEquals(mockedFormatted, formatted, "Times are different");
    }

}
