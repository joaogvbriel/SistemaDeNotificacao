package com.joaogvbriel.sdn.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.joaogvbriel.sdn.dto.NotificationsDTO;
import com.joaogvbriel.sdn.entity.Notifications;
import com.joaogvbriel.sdn.producer.NotificacoesRequestProducer;
import com.joaogvbriel.sdn.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationsService {

    @Autowired private NotificacoesRequestProducer notificacoesRequestProducer;
    @Autowired private NotificationsRepository notificationsRepository;

    public String enviarNotificacao(NotificationsDTO notifications){
        try {
            return notificacoesRequestProducer.sendMessageWithPriority(notifications);
        } catch (JsonProcessingException e) {
            return "Houve um erro ao enviar a notificação." + e.getMessage();
        }
    }

    public String buscaStatus(UUID id){
        Optional<Notifications> notifications = notificationsRepository.findById(id);
        return notifications.get().getStatus();
    }
}
