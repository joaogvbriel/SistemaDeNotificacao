package com.joaogvbriel.sdn.config;

import com.joaogvbriel.sdn.consumer.NotificationsDLT;
import com.joaogvbriel.sdn.consumer.NotificationsHighPriority;
import com.joaogvbriel.sdn.consumer.NotificationsLowPriority;
import com.joaogvbriel.sdn.consumer.NotificationsMediumPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {

    @Autowired private NotificationsLowPriority low;
    @Autowired private NotificationsMediumPriority medium;
    @Autowired private NotificationsHighPriority high;
    @Autowired private NotificationsDLT dlt;

    @Scheduled(fixedDelay = 500)
    public void processarMensagens() {
        if (high.hasMessages()){
            high.process();
        } else if (medium.hasMessages()) {
            medium.process();
        } else if (low.hasMessages()) {
            low.process();
        } else if (dlt.hasMessages()) {
            dlt.process();
        }
    }
}
