package com.project.issue.service;

import com.project.issue.model.Report;
import com.project.issue.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetIssueReports() {
        Report report1 = new Report();
        report1.setIssue("Issue 1");
        report1.setStatus("Open");

        Report report2 = new Report();
        report2.setIssue("Issue 2");
        report2.setStatus("Closed");

        when(reportRepository.findAll()).thenReturn(Arrays.asList(report1, report2));

        List<Map<String, Object>> issueReports = reportService.getIssueReports();

        assertEquals(2, issueReports.size());
        assertEquals("Issue 1", issueReports.get(0).get("issue"));
        assertEquals("Open", issueReports.get(0).get("status"));
        assertEquals("Issue 2", issueReports.get(1).get("issue"));
        assertEquals("Closed", issueReports.get(1).get("status"));

        verify(reportRepository, times(1)).findAll();
    }

    @Test
    public void testGetProgressReports() {
        Report report1 = new Report();
        report1.setTask("Task 1");
        report1.setCompletion(50);

        Report report2 = new Report();
        report2.setTask("Task 2");
        report2.setCompletion(100);

        when(reportRepository.findAll()).thenReturn(Arrays.asList(report1, report2));

        List<Map<String, Object>> progressReports = reportService.getProgressReports();

        assertEquals(2, progressReports.size());
        assertEquals("Task 1", progressReports.get(0).get("task"));
        assertEquals(50, progressReports.get(0).get("completion"));
        assertEquals("Task 2", progressReports.get(1).get("task"));
        assertEquals(100, progressReports.get(1).get("completion"));

        verify(reportRepository, times(1)).findAll();
    }

    @Test
    public void testExportReports() {
        assertTrue(reportService.exportReports("csv"));
        assertTrue(reportService.exportReports("pdf"));
        assertTrue(!reportService.exportReports("txt"));
    }
}
