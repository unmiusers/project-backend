package com.project.issue.service;

import com.project.issue.model.Report;
import com.project.issue.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Map<String, Object>> getIssueReports() {
        return reportRepository.findAll()
                .stream()
                .map(report -> Map.of(
                        "issue", (Object) report.getIssue(),
                        "status", (Object) report.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getProgressReports() {
        return reportRepository.findAll()
                .stream()
                .map(report -> Map.of(
                        "task", (Object) report.getTask(),
                        "completion", (Object) report.getCompletion()
                ))
                .collect(Collectors.toList());
    }

    public boolean exportReports(String format) {
        // Simplified example, in real scenario, you would generate and save/export the report in the specified format
        return format.equalsIgnoreCase("csv") || format.equalsIgnoreCase("pdf");
    }
}
