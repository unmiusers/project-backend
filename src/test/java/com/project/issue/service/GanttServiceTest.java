package com.project.issue.service;

import com.project.issue.model.GanttTask;
import com.project.issue.repository.GanttRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class GanttServiceTest {

    @InjectMocks
    private GanttService ganttService;

    @Mock
    private GanttRepository ganttRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGanttTasks() {
        GanttTask task1 = new GanttTask();
        task1.setId(1L);
        task1.setName("Task 1");

        GanttTask task2 = new GanttTask();
        task2.setId(2L);
        task2.setName("Task 2");

        when(ganttRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        assertEquals(2, ganttService.getAllGanttTasks().size());
        verify(ganttRepository, times(1)).findAll();
    }

    @Test
    public void testGetGanttTaskById() {
        GanttTask task = new GanttTask();
        task.setId(1L);
        task.setName("Test Task");

        when(ganttRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<GanttTask> foundTask = ganttService.getGanttTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals("Test Task", foundTask.get().getName());
    }

    @Test
    public void testCreateGanttTask() {
        GanttTask task = new GanttTask();
        task.setName("New Task");

        when(ganttRepository.save(any(GanttTask.class))).thenReturn(task);

        GanttTask createdTask = ganttService.createGanttTask(task);
        assertEquals("New Task", createdTask.getName());
        verify(ganttRepository, times(1)).save(task);
    }

    @Test
    public void testUpdateGanttTask() {
        GanttTask existingTask = new GanttTask();
        existingTask.setId(1L);
        existingTask.setName("Existing Task");

        GanttTask updatedTask = new GanttTask();
        updatedTask.setName("Updated Task");

        when(ganttRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(ganttRepository.save(any(GanttTask.class))).thenReturn(existingTask);

        Optional<GanttTask> result = ganttService.updateGanttTask(1L, updatedTask);
        assertTrue(result.isPresent());
        assertEquals("Updated Task", result.get().getName());
        verify(ganttRepository, times(1)).findById(1L);
        verify(ganttRepository, times(1)).save(existingTask);
    }

    @Test
    public void testDeleteGanttTask() {
        GanttTask task = new GanttTask();
        task.setId(1L);

        when(ganttRepository.findById(1L)).thenReturn(Optional.of(task));

        boolean isDeleted = ganttService.deleteGanttTask(1L);
        assertTrue(isDeleted);
        verify(ganttRepository, times(1)).findById(1L);
        verify(ganttRepository, times(1)).delete(task);
    }

    @Test
    public void testDeleteGanttTaskNotFound() {
        when(ganttRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = ganttService.deleteGanttTask(1L);
        assertFalse(isDeleted);
        verify(ganttRepository, times(1)).findById(1L);
        verify(ganttRepository, times(0)).delete(any(GanttTask.class));
    }
}
