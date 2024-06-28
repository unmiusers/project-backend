package com.project.issue.controller;

import com.project.issue.model.TimeLog;
import com.project.issue.service.TimeLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TimeLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TimeLogService timeLogService;

    @InjectMocks
    private TimeLogController timeLogController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(timeLogController).build();
    }

    @Test
    public void testGetAllTimeLogs() throws Exception {
        TimeLog timeLog1 = new TimeLog();
        timeLog1.setId(1L);
        timeLog1.setDescription("Worked on task 1");

        TimeLog timeLog2 = new TimeLog();
        timeLog2.setId(2L);
        timeLog2.setDescription("Worked on task 2");

        when(timeLogService.getAllTimeLogs()).thenReturn(Arrays.asList(timeLog1, timeLog2));

        mockMvc.perform(get("/api/timelog")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description", is("Worked on task 1")))
                .andExpect(jsonPath("$[1].description", is("Worked on task 2")));
    }

    @Test
    public void testCreateTimeLog() throws Exception {
        TimeLog timeLog = new TimeLog();
        timeLog.setDescription("New time log");

        when(timeLogService.createTimeLog(any(TimeLog.class))).thenReturn(timeLog);

        mockMvc.perform(post("/api/timelog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"New time log\", \"hours\": 3}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("New time log")));
    }

    @Test
    public void testGetTimeLogReports() throws Exception {
        Map<String, Object> report1 = new HashMap<>();
        report1.put("task", "Task 1");
        report1.put("hours", 3);

        Map<String, Object> report2 = new HashMap<>();
        report2.put("task", "Task 2");
        report2.put("hours", 5);

        when(timeLogService.getTimeLogReports()).thenReturn(Arrays.asList(report1, report2));

        mockMvc.perform(get("/api/timelog/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].task", is("Task 1")))
                .andExpect(jsonPath("$[0].hours", is(3)))
                .andExpect(jsonPath("$[1].task", is("Task 2")))
                .andExpect(jsonPath("$[1].hours", is(5)));
    }

    @Test
    public void testGetTimeLogVisualization() throws Exception {
        Map<String, Object> visualization1 = new HashMap<>();
        visualization1.put("task", "Task 1");
        visualization1.put("progress", 50);

        Map<String, Object> visualization2 = new HashMap<>();
        visualization2.put("task", "Task 2");
        visualization2.put("progress", 75);

        when(timeLogService.getTimeLogVisualization()).thenReturn(Arrays.asList(visualization1, visualization2));

        mockMvc.perform(get("/api/timelog/visualization")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].task", is("Task 1")))
                .andExpect(jsonPath("$[0].progress", is(50)))
                .andExpect(jsonPath("$[1].task", is("Task 2")))
                .andExpect(jsonPath("$[1].progress", is(75)));
    }
}
