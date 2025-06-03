package com.joaogvbriel.sdn.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneNumber;

    public void sendMessage(String mensagem,String numero){
        Twilio.init(accountSid, authToken);
        Message message = Message
                .creator(new com.twilio.type.PhoneNumber(numero),
                        new com.twilio.type.PhoneNumber(phoneNumber),
                        mensagem)
                .create();

        System.out.println(message.getBody());
    }
}
