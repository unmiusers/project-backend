package com.project.issue.dto;

public class TimeLogVisualizationDTO {
    private String task;
    private int hours;

    public TimeLogVisualizationDTO(String task, int hours) {
        this.task = task;
        this.hours = hours;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    // Getters and Setters
}
