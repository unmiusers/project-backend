package com.project.issue.controller;

import com.project.issue.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/issues")
    public ResponseEntity<List<Map<String, Object>>> getIssueReports() {
        return ResponseEntity.ok(reportService.getIssueReports());
    }

    @GetMapping("/progress")
    public ResponseEntity<List<Map<String, Object>>> getProgressReports() {
        return ResponseEntity.ok(reportService.getProgressReports());
    }

    @GetMapping("/export")
    public ResponseEntity<Void> exportReports(@RequestParam String format) {
        if (reportService.exportReports(format)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
