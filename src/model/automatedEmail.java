package model;

import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * This class is used to send an email with the booking confirmation after a booking is created.
 * @author Mattias Malm
 */


public class AutomatedEmail {

    private String from, to, host, username, password;
    private String bookingInformation;
    private Properties properties;

    /**
     * This constructor is used to store the information passed on from the Booking class which is later passed on to the
     * sendEmail-method.
     * @param to - the email address where the booking confirmation is sent.
     * @param bookingInformation - the booking confirmation itself.
     */
    public AutomatedEmail(String to, String bookingInformation) {
        this.to = to;
        this.bookingInformation = bookingInformation;
        sendEmail(to, bookingInformation);
    }

    /**
     * In this method the email is created and sent to the users specified email account.
     * @param to - the email address where the booking confirmation is sent.
     * @param bookingInformation - the booking confirmation itself.
     */

    public void sendEmail(String to, String bookingInformation) {

        // Sender's email ID needs to be mentioned
        from = "flightbuddyapplication@gmail.com";

        // Assuming you are sending email from Gmail's SMTP server
        host = "smtp.gmail.com";

        // Set your Gmail account credentials
        username = "FlightBuddyApplication@gmail.com";

        password = "jypziyxoicijdzrb";

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

            // Create a multipart message
            MimeMultipart multipart = new MimeMultipart();

            // Now set the actual message
            message.setText(bookingInformation);

            // Create the text part of the message
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(bookingInformation);

            // Create the image part of the message
            MimeBodyPart imagePart = new MimeBodyPart();

            // Attaching the FlightBuddy logo to the mail
            imagePart.attachFile("img/icons/FlightBuddy.pdf");

            // Add the parts to the multipart message
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            // Set the content of the message to the multipart
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}




