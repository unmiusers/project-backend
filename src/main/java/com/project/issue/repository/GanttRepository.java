package com.project.issue.repository;

import com.project.issue.model.GanttTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GanttRepository extends JpaRepository<GanttTask, Long> {
}
