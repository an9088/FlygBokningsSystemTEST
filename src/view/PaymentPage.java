package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class PaymentPage {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField cardNumberField;
    private JTextField nameCardField;
    private JButton confirmPayButton;
    private JTextField codeField;
    private JTextField expiryDateField;

    private JPanel paymentPanel;



    private JPanel contactPanel;
    private JPanel paymentInfoPanel;
    private JPanel paymentMethodPanel;
    private JPanel paymentInfoPanel2;
    private JCheckBox masterCardVisaChkBox;
    private JCheckBox amexChkBox;

    private Controller controller;

    private JFrame frame;

    private String fullName;
    private String address;
    private String city;
    private String zip;
    private String country;
    private String email;
    private String destination;
    private String bookingDetails;

    private String name;
    private String lastName;

    private String airport;

    private boolean paymentCompleted;

    private int constructorType;

    public PaymentPage(String fullName, String address, String city, String zip, String country, String email, String destination, String bookingDetails, Controller controller) {
        this.constructorType = 1;
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.email = email;
        this.destination = destination;
        this.bookingDetails = bookingDetails;
        this.controller = controller;
        initializeUI();
        addSuggestionTextFields();
        setupButtonActions();
        this.paymentCompleted = false;


        nameField.setText(fullName);
        emailField.setText(email);


        if(fullName!=null && email!=null){
            nameField.setEditable(false);
            emailField.setEditable(false);
        }else{
            nameField.setText(" ");
            emailField.setText(" ");
        }

    }

    public PaymentPage(String name, String lastName, String address, String city, String zip, String country,
                       String email, String bookingDetails ,String airport, Controller controller){
        this.constructorType = 2;
        this.name = name;
        this.lastName = name;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.airport = airport;
        this.country = country;
        this.email = email;
        this.bookingDetails = bookingDetails;
        this.controller = controller;

        initializeUI();
        addSuggestionTextFields();
        setupButtonActions();
        String fullNameGuest = name + lastName;
        nameField.setText(fullNameGuest);
        emailField.setText(email);


        if(fullNameGuest!=null && email!=null){
            nameField.setEditable(false);
            emailField.setEditable(false);
        }else{
            nameField.setText(" ");
            emailField.setText(" ");
        }

    }

    private void initializeUI() {
        frame = new JFrame("Payment Page");
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        confirmPayButton.setText("Confirm & Pay");
        confirmPayButton.setBackground(new Color(0, 123, 255));
        confirmPayButton.setForeground(Color.WHITE);

        frame.setVisible(true);
    }

    private void addSuggestionTextFields() {
        guiUtils.addSuggestionText(codeField, "CVV");
        guiUtils.addSuggestionText(expiryDateField, "MM/YY");
        guiUtils.addSuggestionText(cardNumberField, "Card Number");
        guiUtils.addSuggestionText(nameCardField, "Name on Card");
        guiUtils.addSuggestionText(nameField, "John Doe");
        guiUtils.addSuggestionText(emailField, "johndoe123@gmail.com");
    }

    private void setupButtonActions() {
        confirmPayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String cardNumber = cardNumberField.getText();
                String nameOnCard = nameCardField.getText();
                String expiryDate = expiryDateField.getText();
                String cvvCode = codeField.getText();

                boolean isAmexCard = amexChkBox.isSelected();
                boolean isMasterCardVisa = masterCardVisaChkBox.isSelected();
                boolean paymentSuccessful = controller.processPayment(name, email, cardNumber, nameOnCard, expiryDate, cvvCode, isAmexCard, isMasterCardVisa);

                if (paymentSuccessful) {
                    JOptionPane.showMessageDialog(frame, "Your booking has been confirmed");

                    if (constructorType == 1) {
                        controller.createNewBooking(fullName, address, city, zip, country, email, destination, bookingDetails, generateBookingNumber());
                    } else if (constructorType == 2) {
                        controller.createNewGuestBooking(name, lastName, address, city, zip, country, email, bookingDetails, airport, generateBookingNumber());
                    }

                    frame.dispose(); // Close the frame if payment is successful
                    paymentCompleted = true;
                }
            }
        });
    }

    private String getSignedInUserFullName() {
        String email = controller.getSignedInEmail();
        String firstName = null;
        String lastName = null;
        String prevLine = null;
        String currentLine = null;
        String prevPrevLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith("Email: ") && currentLine.substring("Email: ".length()).equals(email)) {
                    if (prevPrevLine != null && prevPrevLine.startsWith("Firstname: ")) {
                        firstName = prevPrevLine.substring("Firstname: ".length());
                    }
                    if (prevLine != null && prevLine.startsWith("Lastname: ")) {
                        lastName = prevLine.substring("Lastname: ".length());
                    }
                    break;
                }
                prevPrevLine = prevLine;
                prevLine = currentLine;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return firstName + " " + lastName;
    }


    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }


    private int generateBookingNumber() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Generate a 6-digit random number
    }



}
