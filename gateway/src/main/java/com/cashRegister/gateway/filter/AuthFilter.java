package com.cashRegister.gateway.filter;

import com.cashRegister.gateway.exception.UnauthorizedException;
import com.cashRegister.gateway.routers.Routes;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.Value;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
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


    private String jwtSecret = System.getenv("JWT_KEY");


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(" JWT Token expired");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Invalid JWT Token unsupported");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT Token empty");
        }

    }

    Routes routes = new Routes();
    public List<String> extractRolesFromJwt(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody();

        List<?> rolesObject = (List<?>) claims.get("roles");

        if (rolesObject instanceof List<?>) {
            List<String> roles = (List<String>) rolesObject;
            return roles;
        } else {
            return Collections.emptyList();
        }
    }

    public boolean roleControl(String jwtToken, String requestPath) {

        List<String> roles = extractRolesFromJwt(jwtToken);
        List<String>paths=new ArrayList<>();
        routes.getRoleEndpoints().forEach((key,value)->
        {
            if(roles.stream().anyMatch(r->r.equals(key)))
            {
               value.forEach(path->{
                   paths.add(path);
               }
               );

            }



        });

            if(paths.stream().anyMatch(path->requestPath.contains(path)))
            {
                return true;
            }

        return false;



    }


    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            if(!validatorRoute.isSecureEndpoint.test(exchange.getRequest()))
            {
                return chain.filter(exchange);

            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Authorization header is missing");
            } else {
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }



                   if( validateJwtToken(authHeader))
                   {
                       if(roleControl(authHeader,exchange.getRequest().getPath().value()))
                           {

                               return chain.filter(exchange);
                           }
                           else {

                               throw  new UnauthorizedException();
                           }

                   }


                return chain.filter(exchange);

            }


        });

    }
}

