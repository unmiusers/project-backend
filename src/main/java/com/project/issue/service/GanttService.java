package com.project.issue.service;

import com.project.issue.model.GanttTask;
import com.project.issue.repository.GanttRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GanttService {

    @Autowired
    private GanttRepository ganttRepository;

    public List<GanttTask> getAllGanttTasks() {
        return ganttRepository.findAll();
    }

    public Optional<GanttTask> getGanttTaskById(Long id) {
        return ganttRepository.findById(id);
    }

    public GanttTask createGanttTask(GanttTask ganttTask) {
        return ganttRepository.save(ganttTask);
    }

    public Optional<GanttTask> updateGanttTask(Long id, GanttTask ganttTask) {
        return ganttRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(ganttTask.getName());
                    existingTask.setStart(ganttTask.getStart());
                    existingTask.setEnd(ganttTask.getEnd());
                    return ganttRepository.save(existingTask);
                });
    }

    public boolean deleteGanttTask(Long id) {
        return ganttRepository.findById(id)
                .map(task -> {
                    ganttRepository.delete(task);
                    return true;
                }).orElse(false);
    }
}
