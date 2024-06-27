package com.project.issue.service;

import com.project.issue.model.Notification;
import com.project.issue.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Map<String, Object>> getNotificationSettings() {
        // Simplified example, in a real scenario, you would fetch actual settings from the database or configuration
        return List.of(
                Map.of("id", 1, "name", "Email Notifications", "enabled", true),
                Map.of("id", 2, "name", "SMS Notifications", "enabled", false)
        );
    }

    public boolean toggleNotificationSetting(Long settingId) {
        // Simplified example, in a real scenario, you would update the actual setting in the database or configuration
        return settingId == 1 || settingId == 2;
    }
}
