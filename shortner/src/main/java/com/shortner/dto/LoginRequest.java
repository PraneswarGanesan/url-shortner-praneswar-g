package com.shortner.dto;

import lombok.Value;

@Value
public class LoginRequest {
    private String username;
    private String password;
}
