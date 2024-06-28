package com.project.issue.controller;

import com.project.issue.service.ReportService;
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
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void testGetIssueReports() throws Exception {
        Map<String, Object> report1 = new HashMap<>();
        report1.put("issue", "Issue 1");
        report1.put("status", "Open");

        Map<String, Object> report2 = new HashMap<>();
        report2.put("issue", "Issue 2");
        report2.put("status", "Closed");

        when(reportService.getIssueReports()).thenReturn(Arrays.asList(report1, report2));

        mockMvc.perform(get("/api/reports/issues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].issue", is("Issue 1")))
                .andExpect(jsonPath("$[0].status", is("Open")))
                .andExpect(jsonPath("$[1].issue", is("Issue 2")))
                .andExpect(jsonPath("$[1].status", is("Closed")));
    }

    @Test
    public void testGetProgressReports() throws Exception {
        Map<String, Object> report1 = new HashMap<>();
        report1.put("task", "Task 1");
        report1.put("progress", 50);

        Map<String, Object> report2 = new HashMap<>();
        report2.put("task", "Task 2");
        report2.put("progress", 75);

        when(reportService.getProgressReports()).thenReturn(Arrays.asList(report1, report2));

        mockMvc.perform(get("/api/reports/progress")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].task", is("Task 1")))
                .andExpect(jsonPath("$[0].progress", is(50)))
                .andExpect(jsonPath("$[1].task", is("Task 2")))
                .andExpect(jsonPath("$[1].progress", is(75)));
    }

    @Test
    public void testExportReportsSuccess() throws Exception {
        when(reportService.exportReports(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/reports/export")
                        .param("format", "pdf")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testExportReportsFailure() throws Exception {
        when(reportService.exportReports(anyString())).thenReturn(false);

        mockMvc.perform(get("/api/reports/export")
                        .param("format", "pdf")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
