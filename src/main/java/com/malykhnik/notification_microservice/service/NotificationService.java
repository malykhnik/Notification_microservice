package com.malykhnik.notification_microservice.service;

import com.malykhnik.notification_microservice.dto.MessageDto;

public interface NotificationService {
    void sendToMail(String message);
    void sendToTg(String message);
}
