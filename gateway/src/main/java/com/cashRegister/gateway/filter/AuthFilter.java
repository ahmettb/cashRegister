package com.cashRegister.gateway.filter;

import com.cashRegister.gateway.exception.UnauthorizedException;
import com.cashRegister.gateway.routers.Routes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Log4j2
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    public AuthFilter(ValidatorRoute validatorRoute, RestTemplate restTemplate) {
        super(Config.class);
        this.validatorRoute = validatorRoute;
        this.restTemplate = restTemplate;
    }

    @Data
    public static class Config {
        private List<String> roles;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("roles");
    }

    private final ValidatorRoute validatorRoute;
    private final RestTemplate restTemplate;

    private String jwtSecret ="3nD9sK0wMf7pJ6cGzA8qR5bY2eT4vU1xN7hL2wZ0vX9jQ2mW8rG6uF3eB5tY1pL0";

    Routes routes = new Routes();

    public List<String> extractRolesFromJwt(String jwtToken) {
        log.info("Extracting roles from JWT token");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody();

        List<?> rolesObject = (List<?>) claims.get("roles");

        if (rolesObject instanceof List<?>) {
            List<String> roles = (List<String>) rolesObject;
            log.info("Roles extracted: {}", roles);
            return roles;
        } else {
            log.warn("No roles found in the token");
            return Collections.emptyList();
        }
    }

    public boolean roleControl(String jwtToken, String requestPath) {
        log.info("Performing role control for request path: {}", requestPath);

        List<String> roles = extractRolesFromJwt(jwtToken);
        List<String> paths = new ArrayList<>();
        routes.getRoleEndpoints().forEach((key, value) -> {
            if (roles.stream().anyMatch(r -> r.equals(key))) {
                value.forEach(path -> paths.add(path));
            }
        });

        boolean hasAccess = paths.stream().anyMatch(path -> requestPath.contains(path));
        log.info("Role control result: {}", hasAccess ? "Access granted" : "Access denied");
        return hasAccess;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Request received:{}",request);

            log.info("Request received: URI={}, Method={}", request.getURI(), request.getMethod());

            if (!validatorRoute.isSecureEndpoint.test(request)) {
                log.info("Request is not for a secure endpoint, proceeding without authentication");
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.error("Authorization header is missing");
                throw new RuntimeException("Authorization header is missing");
            } else {
                String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                log.info("Authorization header found");

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    log.info("Validating token");
                    restTemplate.getForObject("http://localhost:8004/api/auth/validate/" + authHeader, String.class);
                } catch (Exception e) {
                    log.error("Token validation failed", e);
                    throw new RuntimeException("Token is not valid");
                }

                if (roleControl(authHeader, request.getPath().value())) {
                    log.info("User authorized, proceeding with the request");
                    return chain.filter(exchange);
                } else {
                    log.warn("User unauthorized to access the requested path");
                    throw new UnauthorizedException();
                }
            }
        };
    }
}
