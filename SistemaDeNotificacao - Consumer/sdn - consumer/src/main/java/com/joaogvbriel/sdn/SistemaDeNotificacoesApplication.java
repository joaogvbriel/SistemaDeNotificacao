package com.joaogvbriel.sdn;

import com.joaogvbriel.sdn.dto.NotificationsDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemaDeNotificacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeNotificacoesApplication.class, args);
	}

}
