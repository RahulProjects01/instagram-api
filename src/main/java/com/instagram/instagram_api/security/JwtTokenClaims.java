package com.instagram.instagram_api.security;

public class JwtTokenClaims {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
