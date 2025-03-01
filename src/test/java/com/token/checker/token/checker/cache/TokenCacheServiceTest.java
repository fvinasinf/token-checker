package com.token.checker.token.checker.cache;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.context.SpringBootTest;

import com.token.checker.token.checker.cache.imp.TokenCacheServiceImpl;
import com.token.checker.token.checker.security.SecurityCheckerService;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
public class TokenCacheServiceTest {

    @InjectMocks
    private TokenCacheServiceImpl tokenCacheService;

    @Mock
    private SecurityCheckerService securityCheckerService;
    
    @Test
    public void getToken() {
        String mockToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdXRoLXZpdmVsaWJyZSIsImV4cCI6MTc0MDg2NzE1MywiaWF0IjoxNzQwODQ5MTUzfQ.pX0NM-1ElbZ02h2UsMGTjTRVedI9FxQ0RZPXqwK5n3rS36EwliXBNbwzEU_szLoXoS6LyN6h3YfgJMotGI3-qg";

        when(securityCheckerService.validateAlloweness(any())).thenReturn(true);
        when(securityCheckerService.validateStructureAndTime(any())).thenReturn(true);

        tokenCacheService.cacheToken(mockToken);

        String token = tokenCacheService.getCachedTokenIfValid();

        assertEquals(mockToken, token, "Tokens not equal taken from cache");
    }

}
