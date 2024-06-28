package com.project.issue.controller;

import com.project.issue.model.GanttTask;
import com.project.issue.service.GanttService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GanttControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GanttService ganttService;

    @InjectMocks
    private GanttController ganttController;

    @Test
    public void accessUnprotectedUrl() throws Exception {
        mockMvc.perform(get("/api/users/unprotected"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an unprotected endpoint"));
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ganttController).build();
    }

    @Test
    public void testGetAllGanttTasks() throws Exception {
        GanttTask task1 = new GanttTask();
        task1.setId(1L);
        task1.setName("Task 1");

        GanttTask task2 = new GanttTask();
        task2.setId(2L);
        task2.setName("Task 2");

        when(ganttService.getAllGanttTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/gantt/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Task 1")))
                .andExpect(jsonPath("$[1].name", is("Task 2")));
    }

    @Test
    public void testCreateGanttTask() throws Exception {
        GanttTask task = new GanttTask();
        task.setName("New Task");

        when(ganttService.createGanttTask(any(GanttTask.class))).thenReturn(task);

        mockMvc.perform(post("/api/gantt/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Task\", \"start\": \"2024-06-01\", \"end\": \"2024-06-07\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Task")));
    }

    @Test
    public void testUpdateGanttTask() throws Exception {
        GanttTask task = new GanttTask();
        task.setId(1L);
        task.setName("Updated Task");

        when(ganttService.updateGanttTask(eq(1L), any(GanttTask.class))).thenReturn(Optional.of(task));

        mockMvc.perform(put("/api/gantt/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Task\", \"start\": \"2024-06-01\", \"end\": \"2024-06-07\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Task")));
    }

    @Test
    public void testUpdateGanttTaskNotFound() throws Exception {
        when(ganttService.updateGanttTask(eq(1L), any(GanttTask.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/gantt/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Task\", \"start\": \"2024-06-01\", \"end\": \"2024-06-07\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteGanttTask() throws Exception {
        when(ganttService.deleteGanttTask(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/gantt/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteGanttTaskNotFound() throws Exception {
        when(ganttService.deleteGanttTask(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/gantt/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
