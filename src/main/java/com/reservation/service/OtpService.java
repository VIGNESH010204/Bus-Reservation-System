package com.reservation.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class OtpService {

    // Twilio credentials
    public static final String ACCOUNT_SID = "ACc4af331adfb7cd2c062a9d478ec4f632";
    public static final String AUTH_TOKEN = "4ca505f4e4934138d111d3c70ab3eb3e";
    public static final String FROM_PHONE = "+14122264818"; // clean Twilio number (must support SMS)

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static String generateOtp() {
        int otp = (int)(Math.random() * 9000) + 1000; // generates 1000-9999
        return String.valueOf(otp);
    }

    public static boolean sendOtp(String toPhone, String otp) {
        if (toPhone.equals(FROM_PHONE)) {
            System.out.println("❌ 'To' and 'From' cannot be the same number.");
            return false;
        }

        try {
            Message message = Message.creator(
                    new PhoneNumber(toPhone),
                    new PhoneNumber(FROM_PHONE),
                    "Your OTP is: " + otp
            ).create();

            System.out.println("✅ OTP sent to: " + toPhone + " SID: " + message.getSid());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Failed to send OTP: " + e.getMessage());
            return false;
        }
    }
}

