package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * @author Ellyas Rahimy
 * @co-author Mattias Malm
 */

public class BookingCreatorGUI {

    private JFrame signUpFrame;

    private Controller controller;

    private String bookingInfo;

    private String airport;



    public BookingCreatorGUI(String bookingInfo, String airport, Controller controller) {
        this.bookingInfo = bookingInfo;
        this.airport = airport;
        this.controller = controller;
        createBookingField();
    }

    public String getBookingInfo() {
        return bookingInfo;
    }

    private void createBookingField() {

        signUpFrame = new JFrame("Make Booking");
        signUpFrame.setSize(400, 400);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
       // signUpPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(7, 7, 7, 7);

        JLabel firstNameLabel = new JLabel("First Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        signUpPanel.add(firstNameLabel, constraints);

        JTextField firstNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        signUpPanel.add(firstNameField, constraints);

        JLabel lastNameLabel = new JLabel("Last Name:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        signUpPanel.add(lastNameLabel, constraints);

        JTextField lastNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        signUpPanel.add(lastNameField, constraints);

        JLabel address = new JLabel("Address:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(address, constraints);

        JTextField addressField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        signUpPanel.add(addressField, constraints);

        JLabel zipCode = new JLabel("Zip Code:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(zipCode, constraints);

        JTextField zipCodeField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        signUpPanel.add(zipCodeField, constraints);

        JLabel city = new JLabel("City:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        signUpPanel.add(city, constraints);

        JTextField cityField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        signUpPanel.add(cityField, constraints);

        JLabel Country = new JLabel("Country:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        signUpPanel.add(Country, constraints);

        JTextField countryField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 5;
        signUpPanel.add(countryField, constraints);

        JLabel eMail = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 6;
        signUpPanel.add(eMail, constraints);

        JTextField eMailField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 6;
        signUpPanel.add(eMailField, constraints);

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

                String name = firstNameField.getText();
                String lastName = lastNameField.getText();
                String address = addressField.getText();
                String city = cityField.getText();
                String zip = zipCodeField.getText();
                String country = countryField.getText();
                String email = eMailField.getText();


                if (name.equals("") || lastName.equals("") || address.equals("") ||
                        city.equals("") || zip.equals("") || country.equals("") || email.equals("")) {
                    String message = "Please enter empty field to complete the booking";
                    errorMessage(message);
                }
                if (!(zip.length() == 5)) {
                    String message = "Zip code must contain five characters";
                    errorMessage(message);
                }
                if (!(email.contains("@"))) {
                   String message = "You must enter a valid email address";
                    errorMessage(message);

                } else if (!(name.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                        || zip.equals("") || country.equals("")) && email.contains("@") && zip.length() == 5) {
                    String airport = controller.getAirport().toString();
                    PaymentPage paymentPage = new PaymentPage( name,  lastName,  address,  city,  zip, country,
                             email,  bookingDetails , airport,  controller);
                    signUpFrame.dispose();

                }
            }
        });

    }

    private int generateBookingNumber() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; // Generate a 6-digit random number
    }


    private void errorMessage(String message) {
        JOptionPane.showMessageDialog(signUpFrame, message);
    }
}
