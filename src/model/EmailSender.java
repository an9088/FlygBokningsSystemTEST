package model;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class EmailSender {

    public static void main(String[] args) {

        // E-postadresser - avsändare och mottagare
        String from = "avsandare@example.com";
        String to = "mottagare@example.com";

        // E-postserverinställningar
        String host = "smtp.example.com";
        String username = "anvandarnamn";
        String password = "losenord";

        // E-postmeddelandets ämne och text
        String subject = "Testmeddelande från Java";
        String message = "Detta är ett testmeddelande som skickas från en Java-applikation.";

        // Skicka e-postmeddelandet
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);

            System.out.println("E-postmeddelandet skickades framgångsrikt.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


