package com.project.issue.model;

import javax.persistence.*;

@Entity
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;

    private Integer hours;

    // Getters and Setters
}
