package com.joaogvbriel.sdn.service;

public interface KafkaConsumerService {
    boolean hasMessages();
    void process();
}
