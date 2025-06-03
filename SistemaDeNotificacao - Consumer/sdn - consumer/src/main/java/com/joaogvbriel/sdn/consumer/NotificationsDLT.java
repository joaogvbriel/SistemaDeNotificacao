package com.joaogvbriel.sdn.consumer;

import com.joaogvbriel.sdn.config.LogsConfig;
import com.joaogvbriel.sdn.config.TwilioConfig;
import com.joaogvbriel.sdn.controller.NotificationsController;
import com.joaogvbriel.sdn.dto.NotificationsDTO;
import com.joaogvbriel.sdn.entity.Notifications;
import com.joaogvbriel.sdn.service.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class NotificationsDLT implements KafkaConsumerService{

    @Autowired private NotificationsController notificationsController;
    @Autowired private LogsConfig logs;
    private final Queue<NotificationsDTO> queue = new ConcurrentLinkedQueue<>();

    @KafkaListener(topics = "notificacoes.request.topic.v1.high.dlt", groupId = "grupo-prioridade-high-dlt",containerFactory = "kafkaListenerContainerFactory")
    public void listenHighDLT(@Payload NotificationsDTO notifications,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.OFFSET) Long offset,
                           Acknowledgment acknowledgment){
        logs.log("Mensagem recebida na prioridade [HIGH-DLT].","INFO");
        notifications.setAck(acknowledgment);
        notifications.setStatus("falhado");
        notificationsController.salvarNotification(notifications);
        queue.add(notifications);
    }

    @KafkaListener(topics = "notificacoes.request.topic.v1.medium.dlt", groupId = "grupo-prioridade-medium-dlt",containerFactory = "kafkaListenerContainerFactory")
    public void listenMediumDLT(@Payload NotificationsDTO notifications,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.OFFSET) Long offset,
                           Acknowledgment acknowledgment){
        logs.log("Mensagem recebida na prioridade [MEDIUM-DLT].","INFO");
        notifications.setAck(acknowledgment);
        notifications.setStatus("falhado");
        notificationsController.salvarNotification(notifications);
        queue.add(notifications);
    }

    @KafkaListener(topics = "notificacoes.request.topic.v1.low.dlt", groupId = "grupo-prioridade-low-dlt",containerFactory = "kafkaListenerContainerFactory")
    public void listenLowDLT(@Payload NotificationsDTO notifications,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.OFFSET) Long offset,
                           Acknowledgment acknowledgment){
        logs.log("Mensagem recebida na prioridade [LOW-DLT].","INFO");
        notifications.setAck(acknowledgment);
        notifications.setStatus("falhado");
        notificationsController.salvarNotification(notifications);
        queue.add(notifications);
    }

    @Override
    public boolean hasMessages() {
        return !queue.isEmpty();
    }

    @Override
    public void process() {
        NotificationsDTO notifications = queue.poll();
        if (notifications != null) {
            try {
                logs.log("Mensagem enviada para a Dead Letter Queue.","INFO");
            } catch (Exception e) {
                Map<String, Object> context = Map.of(
                        "ID", notifications.getId(),
                        "Channel",notifications.getChannel(),
                        "Recipient",notifications.getRecipient(),
                        "Error", e
                );

                logs.logWithMap("Erro ao enviar mensagem para a Dead Letter Queue.","ERROR", context);
            }
        }
    }
}
