package com.token.checker.token.checker.security.beans;

import lombok.Data;

@Data
public class Claims {
    private String sub;
    private Long exp;
    private Long iat;
}
