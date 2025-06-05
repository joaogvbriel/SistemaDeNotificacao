package com.joaogvbriel.sdn.controller;

import com.joaogvbriel.sdn.config.Validations;
import com.joaogvbriel.sdn.dto.NotificationsDTO;
import com.joaogvbriel.sdn.entity.Notifications;
import com.joaogvbriel.sdn.service.NotificationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;
    @Autowired
    private Validations validations;

    @PostMapping
    public String enviar(@Valid @RequestBody NotificationsDTO notifications) {
        if (notifications.getChannel().toUpperCase().trim().equals("SMS") ||
                notifications.getChannel().toUpperCase().trim().equals("EMAIL")) {

            if (notifications.getChannel().toUpperCase().trim().equals("SMS")) {
                if (!validations.isValidPhoneNumber(notifications.getRecipient())) {
                    throw new IllegalArgumentException("Telefone inválido: " + notifications.getRecipient() + ". Deve seguir o formato: +5521999999999");
                }
            } else if (notifications.getChannel().toUpperCase().trim().equals("EMAIL")) {
                if (!validations.isValidEmail(notifications.getRecipient())) {
                    throw new IllegalArgumentException("Email inválido: " + notifications.getRecipient());
                }
            }
        } else {
            throw new IllegalArgumentException("Channel inválido: " + notifications.getChannel() + ". O channel deve ser através de : SMS, EMAIL OU PUSH.");
        }
        return notificationsService.enviarNotificacao(notifications);
    }

    @GetMapping("/{id}")
    public String buscaStatus(@PathVariable UUID id) {
        return "Status: " + notificationsService.buscaStatus(id);
    }
}
