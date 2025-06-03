package com.joaogvbriel.sdn.config;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import net.logstash.logback.marker.Markers;

import java.util.Map;


@Component
public class LogsConfig {

    private static final Logger logger =LoggerFactory.getLogger(LogsConfig.class);

    public void logWithMap(String message, String level, Map<String, Object> context) {
        try {
            context.forEach((key, value) ->
                    MDC.put(key, value != null ? value.toString() : "null")
            );

            switch (level.toUpperCase()) {
                case "ERROR": logger.error(message); break;
                case "WARN": logger.warn(message); break;
                case "DEBUG": logger.debug(message); break;
                default: logger.info(message);
            }

        } finally {
            MDC.clear();
        }
    }

    public void log(String message, String level) {
        try {
            switch (level.toUpperCase()) {
                case "ERROR": logger.error(message); break;
                case "WARN": logger.warn(message); break;
                default: logger.info(message);
            }

        } finally {
            MDC.clear();
        }
    }
}
