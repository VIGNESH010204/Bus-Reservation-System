package com.reservation.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    public static void sendConfirmation(String toEmail, String passengerName) {
        final String fromEmail = "vigneshbcse2021@gmail.com"; // your email
        final String password = "tinc hxwz ejod pqem"; // use App Password, not your Gmail password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Bus Reservation Confirmation");
            message.setText("Dear " + passengerName + ",\n\nYour bus reservation is confirmed!");

            Transport.send(message);
            System.out.println("✅ Confirmation email sent to " + toEmail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
