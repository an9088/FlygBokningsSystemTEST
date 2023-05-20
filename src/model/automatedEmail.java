package model;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class automatedEmail {

    private String from, to, host, username, password;

    private String bookingInformation;

    private Properties properties;


    public automatedEmail(String to, String bookingInformation) {
        this.to = to;
        this.bookingInformation = bookingInformation;
        sendEmail(to, bookingInformation);
    }

    public void sendEmail(String to, String bookingInformation) {
        // Recipient's email ID needs to be mentioned.
        //  to = "malmmattias1@outlook.com";


        // Sender's email ID needs to be mentioned
        from = "flightbuddyapplication@gmail.com";

        // Assuming you are sending email from Gmail's SMTP server
        host = "smtp.gmail.com";

        // Set your Gmail account credentials
        username = "flightbuddyapplication@gmail.com";
        System.out.println(username);
        password = "xuhxunycpqeawvbq";
        System.out.println(password);

        // Get system properties
        properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");

        // Set username and password for authentication

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is your booking!");

            // Now set the actual message
            message.setText(bookingInformation);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}



