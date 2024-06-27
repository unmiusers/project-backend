package com.project.issue.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Commit {

    @Id
    private String id;

    @Lob
    private String message;

    private String author;

    private LocalDateTime date;

    // Getters and Setters
}
