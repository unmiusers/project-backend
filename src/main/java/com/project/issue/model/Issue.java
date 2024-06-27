package com.project.issue.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;

    private String type;

    private String priority;

    private String status;

    private String assignee;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "issue_id")
    private List<Comment> comments = new ArrayList<>();

    // Getters and Setters
}
