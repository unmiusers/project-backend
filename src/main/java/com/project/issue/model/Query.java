package com.project.issue.model;

import javax.persistence.*;

@Entity
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String filters;

    // Getters and Setters
}
