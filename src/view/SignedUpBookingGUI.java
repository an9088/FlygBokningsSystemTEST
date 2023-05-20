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

public class SignedUpBookingGUI {
    private JFrame signUpFrame;

    private Controller controller;

    private String bookingInfo, fullName;



    public SignedUpBookingGUI(String bookingInfo, Controller controller) {
        this.controller = controller;
        this.bookingInfo = bookingInfo;
        createSignedInBookingField();
    }

    public String getBookingInfo() {
        return bookingInfo;
    }

    private void createSignedInBookingField() {

        signUpFrame = new JFrame("Make Booking");
        signUpFrame.setSize(500, 400);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        // signUpPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(7, 7, 7, 7);



        JLabel fullNameLabel = new JLabel("Full Name:");
        //fullNameLabel.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 0;
        signUpPanel.add(fullNameLabel, constraints);

        JTextField fullNameField = new JTextField(20);
        // guiUtils.addSuggestionText(addressField, "Enter your address");
        fullNameField.setText(getSignedInUserFullName());
        fullNameField.setEnabled(false);
        constraints.gridx = 1;
        constraints.gridy = 0;
        signUpPanel.add(fullNameField, constraints);

        JLabel addressLabel = new JLabel("Address:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        signUpPanel.add(addressLabel, constraints);

        JTextField addressField = new JTextField(20);
       // guiUtils.addSuggestionText(addressField, "Enter your address");
        constraints.gridx = 1;
        constraints.gridy = 1;
        signUpPanel.add(addressField, constraints);

        JLabel zipCodeLabel = new JLabel("Zip Code");
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(zipCodeLabel, constraints);

        JTextField zipCodeField = new JTextField(20);
        //guiUtils.addSuggestionText(zipCodeField, "Enter your zip code");
        constraints.gridx = 1;
        constraints.gridy = 2;
        signUpPanel.add(zipCodeField, constraints);

        JLabel cityLabel = new JLabel("City:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(cityLabel, constraints);

        JTextField cityField = new JTextField(20);
        //guiUtils.addSuggestionText(cityField, "Enter your city");
        constraints.gridx = 1;
        constraints.gridy = 3;
        signUpPanel.add(cityField, constraints);

        JLabel countryLabel = new JLabel("Country:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        signUpPanel.add(countryLabel, constraints);

        JTextField countryField = new JTextField(20);
       // guiUtils.addSuggestionText(countryField, "Enter your country");
        constraints.gridx = 1;
        constraints.gridy = 4;
        signUpPanel.add(countryField, constraints);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        signUpPanel.add(emailLabel, constraints);

        JTextField emailField = new JTextField(20);
        emailField.setText(controller.getSignedInEmail());
        emailField.setEnabled(false);
        // guiUtils.addSuggestionText(countryField, "Enter your country");
        constraints.gridx = 1;
        constraints.gridy = 5;
        signUpPanel.add(emailField, constraints);

        JButton createBookingButton = new JButton("Create Booking");
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        signUpPanel.add(createBookingButton, constraints);

        signUpFrame.getContentPane().add(signUpPanel);
        signUpFrame.setLocationRelativeTo(null);
        signUpFrame.setVisible(true);

        createBookingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                fullName = getSignedInUserFullName();
                String email = controller.getSignedInEmail();
                String destination = controller.getAirport();
                String bookingDetails = getBookingInfo();
                String address = addressField.getText();
                String city = cityField.getText();
                String zip = zipCodeField.getText();
                String country = countryField.getText();



                if (address.equals("") || city.equals("") || zip.equals("") || country.equals("")) {
                    String message = "Please enter empty field to complete the booking";
                    errorMessage(message);
                }
                if (!(zip.length() == 5)) {
                    String message = "Zip code must contain five characters";
                    errorMessage(message);
                }
                else if (!(address.equals("") || city.equals("") || country.equals("")) && zip.length() == 5) {


                    JOptionPane.showMessageDialog(signUpFrame, "Your booking has been confirmed");
                    controller.createNewBooking(fullName, address, city, zip, country, email, destination, bookingDetails, generateBookingNumber());

                    signUpFrame.setVisible(false);
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

    private int generateBookingNumber() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Generate a 6-digit random number
    }








    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(signUpFrame, message);
    }
}

