package com.project.issue.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class GanttTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate start;

    private LocalDate end;

    // Getters and Setters
}
