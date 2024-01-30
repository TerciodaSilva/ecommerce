package com.product.notification.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromEmail;
    private String fromName;
    private String replyTo;
    @Column(name = "emailTo")
    private String to;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    private String contentType;
}
