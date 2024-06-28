package com.project.issue.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationSettingDTOTest {

    @Test
    public void testNotificationSettingDTOConstructorAndGetters() {
        Long id = 1L;
        String name = "Email Notifications";
        boolean enabled = true;

        NotificationSettingDTO dto = new NotificationSettingDTO(id, name, enabled);

        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertTrue(dto.isEnabled());
    }

    @Test
    public void testSetId() {
        NotificationSettingDTO dto = new NotificationSettingDTO(1L, "Email Notifications", true);
        Long newId = 2L;
        dto.setId(newId);
        assertEquals(newId, dto.getId());
    }

    @Test
    public void testSetName() {
        NotificationSettingDTO dto = new NotificationSettingDTO(1L, "Email Notifications", true);
        String newName = "SMS Notifications";
        dto.setName(newName);
        assertEquals(newName, dto.getName());
    }

    @Test
    public void testSetEnabled() {
        NotificationSettingDTO dto = new NotificationSettingDTO(1L, "Email Notifications", true);
        dto.setEnabled(false);
        assertFalse(dto.isEnabled());
    }
}
