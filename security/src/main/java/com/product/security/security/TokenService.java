package com.product.security.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.security.model.Role;
import com.product.security.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenService {

    @Value("${application.security.jwt.secret-key}")
    private String secret;
    @Value("${application.security.jwt.expiration}")
    private Integer EXPIRATION_TIME;
    public String generateToken(User user){

        Map<String, ArrayList<String>> claims = new HashMap<>();

        ArrayList<String> roles = new ArrayList<>();
        roles.add(user.getRole().getRole());

        claims.put("roles", roles);

        try {
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getEmail())
                    .withClaim("roles", claims)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC256(secret));


        } catch (JWTCreationException exception) {
            throw new RuntimeException("ERROR WHILE GENERATING TOKEN", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        }

        catch (JWTVerificationException exception) {
            return "";
        }
    }

    public Object getPermissions(String token) throws JsonProcessingException {
        String validate = validateToken(token);
        if (validate.isEmpty()) return validate;

        ObjectMapper objectMapper = new ObjectMapper();
        String roles = JWT.decode(token).getClaim("roles").toString();

        JsonNode jsonNode = objectMapper.readTree(roles);
        String role = jsonNode.get("roles").get(0).toString().replace("\"", "");

        if (Objects.equals(role, Role.ADMIN.getRole())) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }
}