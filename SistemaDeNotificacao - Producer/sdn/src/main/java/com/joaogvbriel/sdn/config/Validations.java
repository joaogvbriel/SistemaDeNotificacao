package com.joaogvbriel.sdn.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Validations {

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleanPhone = phone.replaceAll("[^+0-9]", "");

        if (cleanPhone.startsWith("+55")) {
            String phoneNumber = cleanPhone.substring(3); // Remove +55

            if (phoneNumber.length() == 10 || phoneNumber.length() == 11) {
                return phoneNumber.charAt(0) >= '1' && phoneNumber.charAt(0) <= '9';
            }
        }
        return false;
    }
}
