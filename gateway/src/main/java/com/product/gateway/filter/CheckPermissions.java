package com.product.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CheckPermissions {

    @Value("${ms-security.getAuthRolesUrl}")
    private String url;

    public String getToken(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    public static ArrayList<String> getRole(String role) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> rolesList = objectMapper.readValue(role, new TypeReference<>() {});

        ArrayList<String> listArray = new ArrayList<>();

        for (Map<String, String> roleMap : rolesList) {
            String authority = roleMap.get("authority");
            listArray.add(authority);
        }
        return listArray;
    }

    public GatewayFilter getRoles(String role) {
        return ((exchange, chain) -> WebClient.builder().build().get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, getToken(exchange))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(permissions -> {
                    try {
                        return getGatewayFilterChain(exchange, chain, role, permissions);
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                })
        );
    }

    static Mono<Void> getGatewayFilterChain(ServerWebExchange exchange, GatewayFilterChain chain, String role, String permissions) throws JsonProcessingException {
        if (getRole(permissions).contains(role)) return chain.filter(exchange);
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}
