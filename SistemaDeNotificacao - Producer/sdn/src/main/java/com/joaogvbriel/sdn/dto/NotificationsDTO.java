package com.joaogvbriel.sdn.dto;


import jakarta.validation.constraints.NotBlank;

public class NotificationsDTO {

    @NotBlank(message = "Channel é obrigatório")
    private String channel;

    @NotBlank(message = "Recipient é obrigatório")
    private String recipient;

    @NotBlank(message = "message é obrigatório")
    private String message;

    @NotBlank(message = "Priority é obrigatório")
    private String priority;

    @NotBlank(message = "Data é obrigatório")
    private String data;

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

    public NotificationsDTO(String channel, String recipient, String message, String data, String priority) {
        this.channel = channel;
        this.recipient = recipient;
        this.message = message;
        this.data = data;
        this.priority = priority;
    }
}
