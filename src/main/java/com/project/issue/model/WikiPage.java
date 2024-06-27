package com.project.issue.model;

import javax.persistence.*;

@Entity
public class WikiPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    // Getters and Setters
}
