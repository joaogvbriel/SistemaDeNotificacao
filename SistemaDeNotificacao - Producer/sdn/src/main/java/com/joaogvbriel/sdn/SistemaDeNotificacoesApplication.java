package com.joaogvbriel.sdn;

import com.joaogvbriel.sdn.dto.NotificationsDTO;
import com.joaogvbriel.sdn.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SistemaDeNotificacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeNotificacoesApplication.class, args);
	}


}
