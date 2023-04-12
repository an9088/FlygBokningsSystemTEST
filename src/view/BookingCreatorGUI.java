package src.view;

import src.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ellyas Rahimy
 * @co-author Mattias Malm
 */

public class BookingCreatorGUI {

    private JFrame signUpFrame;

    private Controller controller;

    private int bookingNumber = 1;


    public BookingCreatorGUI(Controller controller) {
        this.controller = controller;
        createBookingField();
    }

    private void createBookingField() {

        signUpFrame = new JFrame("Make Booking");
        signUpFrame.setSize(400, 400);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBackground(Color.LIGHT_GRAY);

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

        JLabel address = new JLabel("Address");
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(address, constraints);

        JTextField addressField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        signUpPanel.add(addressField, constraints);

        JLabel city = new JLabel("City:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(city, constraints);

        JTextField cityField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        signUpPanel.add(cityField, constraints);

        JLabel zipCode = new JLabel("Zip Code:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        signUpPanel.add(zipCode, constraints);

        JTextField zipCodeField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        signUpPanel.add(zipCodeField, constraints);

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

        JButton bookingButton = new JButton("Create Booking");
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        signUpPanel.add(bookingButton, constraints);

        signUpFrame.getContentPane().add(signUpPanel);
        signUpFrame.setLocationRelativeTo(null);
        signUpFrame.setVisible(true);

        bookingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                JOptionPane.showMessageDialog(signUpFrame, "Your booking has been confirmed");

                String name = firstNameField.getText();
                String lastName = lastNameField.getText();
                String address = addressField.getText();
                String city = cityField.getText();
                String zip = zipCodeField.getText();
                String country = countryField.getText();
                String email = eMailField.getText();

                controller.createNewBooking(name, lastName, address, city, zip, country, email, bookingNumber);

                signUpFrame.setVisible(false);
            }
        });
    }
}