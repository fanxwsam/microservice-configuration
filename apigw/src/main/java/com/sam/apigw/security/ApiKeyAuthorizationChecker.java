package com.sam.apigw.security;

public interface ApiKeyAuthorizationChecker {
    boolean isAuthorized(String apiKey, String application);
}
