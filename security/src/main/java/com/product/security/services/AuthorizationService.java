package com.product.security.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.security.dto.AuthetinticationDTO;
import com.product.security.dto.LoginResponseDTO;
import com.product.security.dto.RegisterDTO;
import com.product.security.dto.UserCreatedEvent;
import com.product.security.model.User;
import com.product.security.repositories.UserRepository;
import com.product.security.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String routingKey;

    AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDTO data){
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    public ResponseEntity<User> register (@RequestBody RegisterDTO registerDto){
        if (this.userRepository.findByEmail(registerDto.email()) != null ) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        User user = new User(registerDto.firstName(), registerDto.lastName(), registerDto.email(), encryptedPassword, registerDto.role());
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        this.userRepository.save(user);
        UserCreatedEvent event = new UserCreatedEvent(user.getFirstName(), user.getEmail(), user.getUsername());

        System.out.println(user.getFirstName());


        rabbitTemplate.convertAndSend(routingKey, event);
        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<Object> role (String token) throws JsonProcessingException {
        return ResponseEntity.ok(tokenService.getPermissions(token));
    }
}