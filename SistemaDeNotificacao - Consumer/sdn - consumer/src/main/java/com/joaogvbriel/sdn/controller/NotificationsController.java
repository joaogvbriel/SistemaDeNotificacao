package com.joaogvbriel.sdn.controller;

import com.joaogvbriel.sdn.dto.NotificationsDTO;
import com.joaogvbriel.sdn.entity.Notifications;
import com.joaogvbriel.sdn.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsController {

    @Autowired private NotificationsService notificationsService;

    public Notifications salvarNotification(NotificationsDTO notificationsDto){
        Notifications notifications = new Notifications(
                notificationsDto.getChannel(),
                notificationsDto.getRecipient(),
                notificationsDto.getMessage(),
                notificationsDto.getData(),
                notificationsDto.getPriority(),
                notificationsDto.getStatus()
                );
        return notificationsService.salvarNotification(notifications);
    }
    public String atualizarStatusEnviadoNotification(NotificationsDTO notificationsDto){
        Notifications notifications = new Notifications(
                notificationsDto.getId(),
                notificationsDto.getChannel(),
                notificationsDto.getRecipient(),
                notificationsDto.getMessage(),
                notificationsDto.getData(),
                notificationsDto.getPriority(),
                notificationsDto.getStatus()
        );
        return notificationsService.atualizarStatusEnviadoNotification(notifications);
    }
}
