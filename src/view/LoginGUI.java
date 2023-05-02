package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Klassen LoginGUI skapar ett grafiskt användargränssnitt för att hantera inloggning och registrering.
 */

public class LoginGUI {
    private JFrame loginFrame;
    private JFrame signUpFrame;


    /**
     * Konstruktorn för klassen LoginGUI.
     * Den kallar på createLoginGUI-metoden för att skapa inloggningsgränssnittet.
     */
    public LoginGUI() {
        createLoginGUI();
    }

    private void createLoginGUI() {
        loginFrame = new JFrame("Login");
        //loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        //loginPanel.setBackground(Color.PINK);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        loginPanel.add(emailLabel, constraints);

        JTextField emailField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        loginPanel.add(emailField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        loginPanel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        loginPanel.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        loginPanel.add(loginButton, constraints);



        /**
         * Lyssnare för login-knappen som hanterar inloggningsförsök.
         * När användaren klickar på login-knappen, går denna metod igenom
         * "users.txt"-filen och försöker hitta en matchande e-post och lösenord.
         * Om en match hittas, visas en dialogruta med ett meddelande om att
         * inloggningen lyckades. Om ingen match hittas, visas en dialogruta med
         * ett meddelande om att e-postadressen eller lösenordet är felaktigt.
         */
        // action listener to handle button press
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginFrame, "All fields are required to login.");
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                    String line;
                    boolean foundEmail = false;
                    while ((line = reader.readLine()) != null) {
                        String[] tokens = line.split(": ");
                        if (tokens.length == 2) {
                            String field = tokens[0];
                            String value = tokens[1];
                            if (field.equals("Email") && value.equals(email)) {
                                foundEmail = true;
                            } else if (foundEmail && field.equals("Password") && value.equals(password)) {
                                String name = "";
                                line = reader.readLine();
                                if (line != null) {
                                    tokens = line.split(": ");
                                    if (tokens.length == 2) {
                                        field = tokens[0];
                                        value = tokens[1];
                                        if (field.equals("Firstname")) {
                                            name = value;
                                        }
                                    }
                                }
                                JOptionPane.showMessageDialog(loginFrame, "Login successful, welcome dear Customer!");
                                emailField.setText("");
                                passwordField.setText("");
                                return;
                            } else {
                                foundEmail = false;
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(loginFrame, "Incorrect email or password");
            }
        });


        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (signUpFrame == null || !signUpFrame.isShowing()) {
                    createSignUpGUI();
                }
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        loginPanel.add(signUpButton, constraints);

        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    /**
     * Skapar och visar registreringsgränssnittet.
     * Den innehåller fält för att ange förnamn, efternamn, e-post och lösenord samt en knapp för att registrera sig.
     */
    private void createSignUpGUI() {
        signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(400, 300);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        //signUpPanel.setBackground(Color.PINK);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);


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

        JLabel addressLabel = new JLabel("Address:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(addressLabel, constraints);

        JTextField addressField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        signUpPanel.add(addressField, constraints);

        JLabel zipLabel = new JLabel("Zip Code:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(zipLabel, constraints);

        JTextField zipField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        signUpPanel.add(zipField, constraints);

        JLabel countryLabel = new JLabel("Country:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        signUpPanel.add(countryLabel, constraints);

        JTextField countryField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        signUpPanel.add(countryField, constraints);

        JLabel cityLabel = new JLabel("City:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        signUpPanel.add(cityLabel, constraints);

        JTextField cityField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 5;
        signUpPanel.add(cityField, constraints);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 6;
        signUpPanel.add(emailLabel, constraints);

        JTextField emailField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 6;
        signUpPanel.add(emailField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 7;
        signUpPanel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 7;
        signUpPanel.add(passwordField, constraints);

        JButton registerButton = new JButton("Register");
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        signUpPanel.add(registerButton, constraints);


                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Implement your registration logic here
                        String firstName = firstNameField.getText().trim();
                        String lastName = lastNameField.getText().trim();
                        String address = addressField.getText().trim();
                        String zip = zipField.getText().trim();
                        String country = countryField.getText().trim();
                        String city = cityField.getText().trim();
                        String email = emailField.getText().trim();
                        String password = new String(passwordField.getPassword()).trim();


                        // Check that all fields are filled
                        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || zip.isEmpty() ||
                                country.isEmpty() || city.isEmpty() || email.isEmpty() || password.isEmpty()) {
                            JOptionPane.showMessageDialog(signUpFrame, "All fields must be filled in.");
                            return;
                        }

                        // Check that the email contains the @ symbol
                        if (!email.contains("@")) {
                            JOptionPane.showMessageDialog(signUpFrame, "Please enter a valid email address.");
                            return;
                        }

                        // Check that the zip code contains exactly 5 digits
                        if (zip.length() != 5 || !zip.matches("\\d{5}")) {
                            JOptionPane.showMessageDialog(signUpFrame, "Please enter a valid zip code");
                            return;
                        }


                        // Save the user data to a text file
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                            writer.write("Firstname: " + firstName);
                            writer.newLine();
                            writer.write("Lastname: " + lastName);
                            writer.newLine();
                            writer.write("Address: " + address);
                            writer.newLine();
                            writer.write("ZipCode: " + zip);
                            writer.newLine();
                            writer.write("Country: " + country);
                            writer.newLine();
                            writer.write("City: " + city);
                            writer.newLine();
                            writer.write("Email: " + email);
                            writer.newLine();
                            writer.write("Password: " + password);
                            writer.newLine();
                            writer.newLine();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(signUpFrame, "Error saving user data: " + ex.getMessage());
                            return;
                        }

                        JOptionPane.showMessageDialog(signUpFrame, "Registration Successful!");
                        signUpFrame.dispose();

                        // Close the SignUpFrame and open the LoginFrame
                        signUpFrame.setVisible(false);
                        loginFrame.setVisible(true);
                    }
                });


        signUpFrame.getContentPane().add(signUpPanel);
        signUpFrame.setLocationRelativeTo(null);
        signUpFrame.setVisible(true);
    }


}

