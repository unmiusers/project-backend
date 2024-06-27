package com.project.issue.service;

import com.project.issue.model.Notification;
import com.project.issue.dto.NotificationSettingDTO;
import com.project.issue.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<NotificationSettingDTO> getNotificationSettings() {
        // Simplified example, in a real scenario, you would fetch actual settings from the database or configuration
        return List.of(
                new NotificationSettingDTO(1L, "Email Notifications", true),
                new NotificationSettingDTO(2L, "SMS Notifications", false)
        );
    }

    public boolean toggleNotificationSetting(Long settingId) {
        // Simplified example, in a real scenario, you would update the actual setting in the database or configuration
        return settingId == 1 || settingId == 2;
    }
}