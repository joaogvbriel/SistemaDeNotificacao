package com.joaogvbriel.sdn.Tests;

import com.joaogvbriel.sdn.consumer.NotificationsHighPriority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NotificationHighPriorityTest {

    @Autowired
    private NotificationsHighPriority consumer;

//    @Test
//    public void testQueueEnfileiramentoEProcessamento() {
//        String mensagem = "{\"channel\":\"SMS\",\"message\":\"testando\",\"priority\":\"high\"}";
//
//        consumer.listenHigh(mensagem);
//        assertTrue(consumer.hasMessages());
//
//        consumer.process();
//        assertFalse(consumer.hasMessages());
//    }
}
