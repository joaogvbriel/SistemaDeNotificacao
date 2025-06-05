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
public class NotificationsLowPriority implements KafkaConsumerService{

    @Autowired private NotificationsController notificationsController;

    @Autowired
    private TwilioConfig twilioConfig;
    private final Queue<NotificationsDTO> queue = new ConcurrentLinkedQueue<>();
    @Autowired private EmailService emailService;
    @Autowired private LogsConfig logs;

    @KafkaListener(topics = "notificacoes.request.topic.v1.low", groupId = "grupo-prioridade-low",containerFactory = "kafkaListenerContainerFactory")
    public void listenLow(@Payload NotificationsDTO notifications,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.OFFSET) Long offset,
                          Acknowledgment acknowledgment){
        logs.log("Mensagem recebida na prioridade [LOW].","INFO");

        notifications.setAck(acknowledgment);
        notifications.setStatus("pendente");
        Notifications notificationsSalva = notificationsController.salvarNotification(notifications);
        notifications.setId(notificationsSalva.getUuid());
        notifications.setMessage(notifications.getMessage() + " Para ver o status consulte o id= " + notifications.getId());
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
            logs.log("Processando mensagem na prioridade [LOW].","INFO");
            try {
                if (notifications.getChannel().toUpperCase().trim().equals("EMAIL")){
                    emailService.sendEmail(notifications);
                }
                if (notifications.getChannel().equals("SMS")){
                    twilioConfig.sendMessage(notifications.getMessage(),notifications.getRecipient());
                }
                notifications.getAck().acknowledge();
                notificationsController.atualizarStatusEnviadoNotification(notifications);
                logs.log("Mensagem processada com sucesso na prioridade [LOW].","INFO");
            } catch (Exception e) {
                Map<String, Object> context = Map.of(
                        "ID", notifications.getId(),
                        "Channel",notifications.getChannel(),
                        "Recipient",notifications.getRecipient(),
                        "Error", e
                );

                logs.logWithMap("Erro ao processar mensagem na prioridade [LOW].","ERROR", context);
            }
        }
    }
    private DefaultErrorHandler defaultErrorHandler(){
        return new DefaultErrorHandler(new FixedBackOff(1000L,2));
    }
}
