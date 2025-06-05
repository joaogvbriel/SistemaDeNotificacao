package com.joaogvbriel.sdn.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaogvbriel.sdn.dto.NotificationsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacoesRequestProducer {

    @Value("${topicos.notificacoes.request.topic.low}")
    private String topicoNotificacoesRequestLow;

    @Value("${topicos.notificacoes.request.topic.medium}")
    private String topicoNotificacoesRequestMedium;
    @Value("${topicos.notificacoes.request.topic.high}")
    private String topicoNotificacoesRequestHigh;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessageWithPriority(NotificationsDTO notifications) throws JsonProcessingException {
        String conteudo = objectMapper.writeValueAsString(notifications);
        switch (notifications.getPriority()) {
            case "low":
                kafkaTemplate.send(topicoNotificacoesRequestLow, conteudo);
                break;

            case "medium":
                kafkaTemplate.send(topicoNotificacoesRequestMedium, conteudo);
                break;

            case "high":
                kafkaTemplate.send(topicoNotificacoesRequestHigh, conteudo);
                break;
            default:
                kafkaTemplate.send(topicoNotificacoesRequestLow, conteudo);
                break;
        }
        return "Noticação enviada para processamento";
    }
}
