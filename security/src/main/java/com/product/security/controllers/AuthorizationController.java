package com.product.security.controllers;

import com.product.security.dto.AuthetinticationDTO;
import com.product.security.dto.RegisterDTO;
import com.product.security.model.User;
import com.product.security.security.TokenService;
import com.product.security.services.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("auth")
public class AuthorizationController {
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDTO authetinticationDto){
        return authorizationService.login(authetinticationDto);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register (@RequestBody RegisterDTO registerDto){
        return authorizationService.register(registerDto);
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles(@RequestHeader("Authorization") String authorizationToken) throws IOException {
        String token = authorizationToken.replace("Bearer ", "").replaceAll("\"","");
        return authorizationService.role(token);
    }
}