package com.project.issue.model;

import javax.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String issue;

    private String status;

    private String task;

    private Integer completion;

    // Getters and Setters
}
