package com.project.issue.service;

import com.project.issue.model.TimeLog;
import com.project.issue.repository.TimeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimeLogService {

    @Autowired
    private TimeLogRepository timeLogRepository;

    public List<TimeLog> getAllTimeLogs() {
        return timeLogRepository.findAll();
    }

    public TimeLog createTimeLog(TimeLog timeLog) {
        return timeLogRepository.save(timeLog);
    }

    public List<Map<String, Object>> getTimeLogReports() {
        return timeLogRepository.findAll()
                .stream()
                .map(log -> Map.of(
                        "description", (Object) log.getDescription(),
                        "hours", (Object) log.getHours()
                ))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getTimeLogVisualization() {
        return timeLogRepository.findAll()
                .stream()
                .map(log -> Map.of(
                        "task", (Object) log.getDescription(),
                        "hours", (Object) log.getHours()
                ))
                .collect(Collectors.toList());
    }
}
