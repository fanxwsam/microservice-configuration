package com.sam.apigw.security;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class ApiKeyAuthorizationFilter implements GlobalFilter, Ordered {
    private final FakeApiAuthorizationChecker fakeApiAuthorizationChecker;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("ApiKeyAuthorizationFilter..... check the key...");

        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String application = route.getId();

        List<String> apiKey = exchange.getRequest().getHeaders().get("apiKey");


        if (application == null
                || apiKey == null
                || apiKey.isEmpty()
                || !fakeApiAuthorizationChecker.isAuthorized(apiKey.get(0).toString(), application)
        ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not authorized!");
        }

        System.out.println("API Key: " + apiKey.get(0));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
