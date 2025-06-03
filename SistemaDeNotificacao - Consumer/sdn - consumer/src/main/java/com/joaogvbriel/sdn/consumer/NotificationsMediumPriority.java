package com.joaogvbriel.sdn.consumer;

import com.joaogvbriel.sdn.config.LogsConfig;
import com.joaogvbriel.sdn.config.TwilioConfig;
import com.joaogvbriel.sdn.controller.NotificationsController;
import com.joaogvbriel.sdn.dto.NotificationsDTO;
import com.joaogvbriel.sdn.entity.Notifications;
import com.joaogvbriel.sdn.service.EmailService;
import com.joaogvbriel.sdn.service.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class NotificationsMediumPriority implements KafkaConsumerService{

    @Autowired private NotificationsController notificationsController;
    @Autowired private TwilioConfig twilioConfig;
    @Autowired private EmailService emailService;
    @Autowired private LogsConfig logs;

    private final Queue<NotificationsDTO> queue = new ConcurrentLinkedQueue<>();

    @KafkaListener(topics = "notificacoes.request.topic.v1.medium", groupId = "grupo-prioridade-medium",containerFactory = "kafkaListenerContainerFactory")
    public void listenMedium(@Payload NotificationsDTO notifications,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.OFFSET) Long offset,
                             Acknowledgment acknowledgment){
        logs.log("Mensagem recebida na prioridade [MEDIUM].","INFO");

        notifications.setAck(acknowledgment);
        notifications.setStatus("pendente");
        Notifications notificationsSalva = notificationsController.salvarNotification(notifications);
        notifications.setId(notificationsSalva.getUuid());
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
            logs.log("Processando mensagem na prioridade [MEDIUM].","INFO");
                try {
                if (notifications.getChannel().toUpperCase().trim().equals("SMS")){
                    twilioConfig.sendMessage(notifications.getMessage(),notifications.getRecipient());
                }
                    if (notifications.getChannel().toUpperCase().trim().equals("EMAIL")){
                        emailService.sendEmail(notifications);
                    }
                    notifications.getAck().acknowledge();
                    notificationsController.atualizarStatusEnviadoNotification(notifications);
                    logs.log("Mensagem processada com sucesso na prioridade [MEDIUM].","INFO");
                } catch (Exception e) {
                    Map<String, Object> context = Map.of(
                            "ID", notifications.getId(),
                            "Channel",notifications.getChannel(),
                            "Recipient",notifications.getRecipient(),
                            "Error", e
                    );

                    logs.logWithMap("Erro ao processar mensagem na prioridade [MEDIUM].","ERROR", context);
                }
        }
    }
    private DefaultErrorHandler defaultErrorHandler(){
        return new DefaultErrorHandler(new FixedBackOff(1000L,2));
    }
}
