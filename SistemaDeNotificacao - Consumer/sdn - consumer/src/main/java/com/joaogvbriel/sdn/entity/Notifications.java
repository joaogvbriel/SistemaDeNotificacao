package com.joaogvbriel.sdn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String channel;
    private String recipient;
    private String message;
    private String priority;
    private String data;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTentativas() {
        return tentativas;
    }

    public void setTentativas(String tentativas) {
        this.tentativas = tentativas;
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

    public Notifications(String channel, String recipient, String message, String data, String priority,String status) {
        this.channel = channel;
        this.recipient = recipient;
        this.message = message;
        this.data = data;
        this.priority = priority;
        this.status = status;
    }

    public Notifications(UUID uuid,String channel, String recipient, String message, String data, String priority,String status) {
        this.uuid = uuid;
        this.channel = channel;
        this.recipient = recipient;
        this.message = message;
        this.data = data;
        this.priority = priority;
        this.status = status;
    }

    public Notifications() {
    }
}
