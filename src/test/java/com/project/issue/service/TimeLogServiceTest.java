package com.project.issue.service;

import com.project.issue.model.TimeLog;
import com.project.issue.repository.TimeLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TimeLogServiceTest {

    @InjectMocks
    private TimeLogService timeLogService;

    @Mock
    private TimeLogRepository timeLogRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTimeLogs() {
        TimeLog log1 = new TimeLog();
        log1.setId(1L);
        log1.setDescription("Log 1");

        TimeLog log2 = new TimeLog();
        log2.setId(2L);
        log2.setDescription("Log 2");

        when(timeLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<TimeLog> logs = timeLogService.getAllTimeLogs();
        assertEquals(2, logs.size());
        verify(timeLogRepository, times(1)).findAll();
    }

    @Test
    public void testCreateTimeLog() {
        TimeLog log = new TimeLog();
        log.setDescription("New Log");

        when(timeLogRepository.save(any(TimeLog.class))).thenReturn(log);

        TimeLog createdLog = timeLogService.createTimeLog(log);
        assertEquals("New Log", createdLog.getDescription());
        verify(timeLogRepository, times(1)).save(log);
    }

    @Test
    public void testGetTimeLogReports() {
        TimeLog log1 = new TimeLog();
        log1.setDescription("Task 1");
        log1.setHours(5);

        TimeLog log2 = new TimeLog();
        log2.setDescription("Task 2");
        log2.setHours(3);

        when(timeLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<Map<String, Object>> reports = timeLogService.getTimeLogReports();
        assertEquals(2, reports.size());
        assertEquals("Task 1", reports.get(0).get("description"));
        assertEquals(5, reports.get(0).get("hours"));
        assertEquals("Task 2", reports.get(1).get("description"));
        assertEquals(3, reports.get(1).get("hours"));

        verify(timeLogRepository, times(1)).findAll();
    }

    @Test
    public void testGetTimeLogVisualization() {
        TimeLog log1 = new TimeLog();
        log1.setDescription("Task 1");
        log1.setHours(5);

        TimeLog log2 = new TimeLog();
        log2.setDescription("Task 2");
        log2.setHours(3);

        when(timeLogRepository.findAll()).thenReturn(Arrays.asList(log1, log2));

        List<Map<String, Object>> visualization = timeLogService.getTimeLogVisualization();
        assertEquals(2, visualization.size());
        assertEquals("Task 1", visualization.get(0).get("task"));
        assertEquals(5, visualization.get(0).get("hours"));
        assertEquals("Task 2", visualization.get(1).get("task"));
        assertEquals(3, visualization.get(1).get("hours"));

        verify(timeLogRepository, times(1)).findAll();
    }
}
