package com.malykhnik.notification_microservice.controller;

import com.malykhnik.notification_microservice.dto.MessageDto;
import com.malykhnik.notification_microservice.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/sendToMail")
    public ResponseEntity<String> sendToMail(@RequestBody MessageDto messageDto) {
        notificationService.sendToMail(messageDto.getMessage());
        return ResponseEntity.ok("Сообщение успешно отправлено");
    }

    @PostMapping("/sendToTG")
    public ResponseEntity<String> sendToTg(@RequestBody MessageDto messageDto) {
        notificationService.sendToTg(messageDto.getMessage());
        return ResponseEntity.ok("Сообщение успешно отправлено");
    }
}
