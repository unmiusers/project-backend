package com.project.issue.service;

import com.project.issue.dto.NotificationSettingDTO;
import com.project.issue.model.Notification;
import com.project.issue.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setMessage("Notification 1");

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setMessage("Notification 2");

        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification1, notification2));

        List<Notification> notifications = notificationService.getAllNotifications();
        assertEquals(2, notifications.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    public void testGetNotificationSettings() {
        List<NotificationSettingDTO> settings = notificationService.getNotificationSettings();

        assertEquals(2, settings.size());
        assertEquals("Email Notifications", settings.get(0).getName());
        assertTrue(settings.get(0).isEnabled());
        assertEquals("SMS Notifications", settings.get(1).getName());
        assertTrue(!settings.get(1).isEnabled());
    }

    @Test
    public void testToggleNotificationSetting() {
        assertTrue(notificationService.toggleNotificationSetting(1L));
        assertTrue(notificationService.toggleNotificationSetting(2L));
        assertTrue(!notificationService.toggleNotificationSetting(3L));
    }
}
