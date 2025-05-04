package com.malykhnik.notification_microservice.service.impl;

import com.malykhnik.notification_microservice.dto.MessageDto;
import com.malykhnik.notification_microservice.entity.Email;
import com.malykhnik.notification_microservice.repository.EmailRepo;
import com.malykhnik.notification_microservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final EmailRepo emailRepo;
    private final JavaMailSender mailSender;
    @Value("${email.from}")
    String EMAIL_FROM;
    @Value("${url.tg.key}")
    String URL_TG;
    @Value("${url.tg_req.key}")
    String TG_REQUEST;
    @Override
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void sendToMail(String messageTxt) {
        LOGGER.info("ВЫЗВАНА ФУНКЦИЯ sendMail ");
        LOGGER.info("Получил из Kafka: " + messageTxt);
        Thread thread = new Thread(() -> {
            List<Email> emails = emailRepo.findAll();
            for (Email email : emails) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(EMAIL_FROM);
                message.setTo(email.getReceiver());
                message.setSubject("Отключение сервиса");
                message.setText(messageTxt);
                try {
                    LOGGER.info("ПОПЫТКА ОТПРАВИТЬ СООБЩЕНИЕ");
                    mailSender.send(message);
                    LOGGER.info("Письмо отправлено успешно");
                } catch (Exception e) {
                    LOGGER.error("Ошибка при отправке письма", e);
                }
            }
        });
        thread.start();
    }

    @Override
    public void sendToTg(String message) {
        try {
            WebClient.create(URL_TG).post()
                    .uri(TG_REQUEST)
                    .header("Content-Type", "application/json")
                    .bodyValue(message)
                    .retrieve()
                    .bodyToMono(ClientResponse.class)
                    .subscribe(
                            responseBody -> LOGGER.info("Notification send"),
                            error -> LOGGER.info("Error: " + error.getMessage())
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
