package com.project.issue.controller;

import com.project.issue.dto.NotificationSettingDTO;
import com.project.issue.model.Notification;
import com.project.issue.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setMessage("Notification 1");

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setMessage("Notification 2");

        when(notificationService.getAllNotifications()).thenReturn(Arrays.asList(notification1, notification2));

        mockMvc.perform(get("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].message", is("Notification 1")))
                .andExpect(jsonPath("$[1].message", is("Notification 2")));
    }

    @Test
    public void testGetNotificationSettings() throws Exception {
        NotificationSettingDTO setting1 = new NotificationSettingDTO(1L, "Email Notifications", true);
        NotificationSettingDTO setting2 = new NotificationSettingDTO(2L, "SMS Notifications", false);

        when(notificationService.getNotificationSettings()).thenReturn(Arrays.asList(setting1, setting2));

        mockMvc.perform(get("/api/notifications/settings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Email Notifications")))
                .andExpect(jsonPath("$[1].name", is("SMS Notifications")));
    }

    @Test
    public void testToggleNotificationSetting() throws Exception {
        when(notificationService.toggleNotificationSetting(anyLong())).thenReturn(true);

        mockMvc.perform(put("/api/notifications/settings/1/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testToggleNotificationSettingNotFound() throws Exception {
        when(notificationService.toggleNotificationSetting(anyLong())).thenReturn(false);

        mockMvc.perform(put("/api/notifications/settings/1/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
