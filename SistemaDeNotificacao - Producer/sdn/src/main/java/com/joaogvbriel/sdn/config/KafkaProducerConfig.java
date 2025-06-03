package com.joaogvbriel.sdn.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Value("${topicos.notificacoes.request.topic.low}")
    private String notificacoesRequestTopicLow;
    @Value("${topicos.notificacoes.request.topic.medium}")
    private String notificacoesRequestTopicMedium;
    @Value("${topicos.notificacoes.request.topic.high}")
    private String notificacoesRequestTopicHigh;

    @Value("${topicos.notificacoes.request.topic.low.dlt}")
    private String notificacoesRequestTopicLowDLT;
    @Value("${topicos.notificacoes.request.topic.medium.dlt}")
    private String notificacoesRequestTopicMediumDLT;
    @Value("${topicos.notificacoes.request.topic.high.dlt}")
    private String notificacoesRequestTopicHighDLT;


    @Bean
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic notificacoesRequestTopicBuilderLow() {
        return TopicBuilder.name(notificacoesRequestTopicLow)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificacoesRequestTopicBuilderMedium() {
        return TopicBuilder.name(notificacoesRequestTopicMedium)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificacoesRequestTopicBuilderHigh() {
        return TopicBuilder.name(notificacoesRequestTopicHigh)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificacoesRequestTopicBuilderLowDLT() {
        return TopicBuilder.name(notificacoesRequestTopicLowDLT)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificacoesRequestTopicBuilderMediumDLT() {
        return TopicBuilder.name(notificacoesRequestTopicMediumDLT)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic notificacoesRequestTopicBuilderHighDLT() {
        return TopicBuilder.name(notificacoesRequestTopicHighDLT)
                .partitions(1)
                .replicas(1)
                .build();
    }


}
