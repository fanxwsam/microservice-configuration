package com.sam.apigw.security;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("fake")
public class FakeApiAuthorizationChecker implements ApiKeyAuthorizationChecker{

    private final static Map<String, List<String>> keys = Map.of(
                           "supersecure", List.of("customer")
    );

    @Override
    public boolean isAuthorized(String apiKey, String application) {
        return keys.getOrDefault(apiKey, List.of())
                .stream()
                .anyMatch( k -> k.contains(application) );
    }
}
