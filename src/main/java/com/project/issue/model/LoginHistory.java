package com.project.issue.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String ip;

    // Getters and Setters
}

