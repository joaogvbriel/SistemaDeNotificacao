package com.joaogvbriel.sdn.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.kafka.support.Acknowledgment;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationsDTO {
    private UUID id;
    private String channel;
    private String recipient;
    private String message;
    private String priority;
    private String data;
    private Acknowledgment ack;
    private String status;
    private String tentativas;
    private String erros;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Acknowledgment getAck() {
        return ack;
    }

    public void setAck(Acknowledgment ack) {
        this.ack = ack;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErros() {
        return erros;
    }

    public void setErros(String erros) {
        this.erros = erros;
    }

    public String getTentativas() {
        return tentativas;
    }

    public void setTentativas(String tentativas) {
        this.tentativas = tentativas;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NotificationsDTO(String channel, String data, String message, String priority, String recipient, String status) {
        this.channel = channel;
        this.data = data;
        this.message = message;
        this.priority = priority;
        this.recipient = recipient;
        this.status = status;
    }

    public NotificationsDTO() {}

    @Override
    public String toString() {
        return "NotificationsDTO{" +
                "id=" + id +
                ", channel='" + channel + '\'' +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", priority='" + priority + '\'' +
                ", data='" + data + '\'' +
                ", ack=" + ack +
                ", status='" + status + '\'' +
                ", tentativas='" + tentativas + '\'' +
                ", erros='" + erros + '\'' +
                '}';
    }
}
