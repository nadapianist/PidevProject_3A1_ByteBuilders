package tn.esprit.services;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class emailService {
    public static void sendVerificationEmail(String toEmail, String verificationCode) {
        if (toEmail == null) {
            System.err.println("Recipient email is null. Cannot send verification email.");
            return;
        }

        final String username = "boujdaysarra35@gmail.com"; // Your email
        final String password = "kdxfwllkrgbxgqfx"; // Your password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP host for Gmail
        props.put("mail.smtp.port", "587"); // SMTP port for Gmail

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[Tunism] Password Reset Verification Code");
            message.setText("Dear User,\n\nYour verification code is: " + verificationCode + "\n\nRegards,\nTunism");

            Transport.send(message);

            System.out.println("Verification email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending verification email", e);
        }
    }
}
