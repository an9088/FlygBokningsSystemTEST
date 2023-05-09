package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignedUpBookingGUI {
    private JFrame signUpFrame;

    private Controller controller;

    private String bookingInfo, signedInBookingInfo;

    private static int bookingNumber = 0;


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
        signUpFrame.setSize(400, 250);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        // signUpPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel addressLabel = new JLabel("Address:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        signUpPanel.add(addressLabel, constraints);

        JTextField addressField = new JTextField(20);
       // guiUtils.addSuggestionText(addressField, "Enter your address");
        constraints.gridx = 1;
        constraints.gridy = 0;
        signUpPanel.add(addressField, constraints);

        JLabel zipCodeLabel = new JLabel("Zip Code");
        constraints.gridx = 0;
        constraints.gridy = 1;
        signUpPanel.add(zipCodeLabel, constraints);

        JTextField zipCodeField = new JTextField(20);
        //guiUtils.addSuggestionText(zipCodeField, "Enter your zip code");
        constraints.gridx = 1;
        constraints.gridy = 1;
        signUpPanel.add(zipCodeField, constraints);

        JLabel cityLabel = new JLabel("City:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(cityLabel, constraints);

        JTextField cityField = new JTextField(20);
        //guiUtils.addSuggestionText(cityField, "Enter your city");
        constraints.gridx = 1;
        constraints.gridy = 2;
        signUpPanel.add(cityField, constraints);

        JLabel countryLabel = new JLabel("Country:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(countryLabel, constraints);

        JTextField countryField = new JTextField(20);
       // guiUtils.addSuggestionText(countryField, "Enter your country");
        constraints.gridx = 1;
        constraints.gridy = 3;
        signUpPanel.add(countryField, constraints);

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

                    bookingNumber++;
                    JOptionPane.showMessageDialog(signUpFrame, "Your booking has been confirmed");
                    //controller.createNewBooking(name, lastName, address, city, zip, country, email, bookingDetails, bookingNumber);

                    signUpFrame.setVisible(false);
                }
            }
        });

    }




    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(signUpFrame, message);
    }
}

