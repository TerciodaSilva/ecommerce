package com.product.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean validateToken(String token) {
        ResponseEntity<Boolean> response = restTemplate.postForEntity(
                "http://localhost:8000/authenticator", token, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }
}