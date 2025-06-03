package com.joaogvbriel.sdn.service;

import com.joaogvbriel.sdn.entity.Notifications;
import com.joaogvbriel.sdn.repository.NotificationsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    @Autowired private NotificationsRepository notificationsRepository;

    public Notifications salvarNotification(Notifications notifications){
        return notificationsRepository.save(notifications);
    }

    @Transactional
    public String atualizarStatusEnviadoNotification(Notifications notificationsAtualizada){
        System.out.println("UUID = " + notificationsAtualizada.getUuid());
        Notifications notifications = notificationsRepository.getReferenceById(notificationsAtualizada.getUuid());
        notifications.setStatus("enviado");
        try {
            notificationsRepository.save(notifications);
            return "Atualizado com sucesso.";
        } catch (Exception e) {
            return "Não foi possível atualizar.";
        }
    }

}
