package com.product.notification.dtos;

import lombok.Data;

@Data
public class UserCreatedEvent {

    private String userName;
    private String email;
    private String name;

    public UserCreatedEvent(String userName, String email, String name) {
        this.name = name;
        this.email = email;
        this.userName = userName;
    }
}