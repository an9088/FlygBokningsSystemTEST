package src.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoginGUI {
    private JFrame loginFrame;
    private JFrame signUpFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().createLoginGUI());
    }

    private void createLoginGUI() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.LIGHT_GRAY);

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

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSignUpGUI();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        loginPanel.add(signUpButton, constraints);

        loginFrame.getContentPane().add(loginPanel);
        loginFrame.setVisible(true);
    }

    private void createSignUpGUI() {
        signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(400, 300);

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBackground(Color.LIGHT_GRAY);

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

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        signUpPanel.add(emailLabel, constraints);

        JTextField emailField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        signUpPanel.add(emailField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        signUpPanel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        signUpPanel.add(passwordField, constraints);

        JButton registerButton = new JButton("Register");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        signUpPanel.add(registerButton, constraints);


                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Implement your registration logic here
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String email = emailField.getText();
                        String password = new String(passwordField.getPassword());

                        // Save the user data to a text file

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(".\\users.txt", true))) {
                            writer.write("Firstname: " + firstName);
                            writer.newLine();
                            writer.write("Lastname: " + lastName );
                            writer.newLine();
                            writer.write("Email: " + email);
                            writer.newLine();
                            writer.write("Password: " + password);
                            writer.newLine();writer.newLine();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(signUpFrame, "Error saving user data: " + ex.getMessage());
                            return;
                        }

                        JOptionPane.showMessageDialog(signUpFrame, "Registration Successful!");
                        signUpFrame.dispose();
                    }
                });


        signUpFrame.getContentPane().add(signUpPanel);
        signUpFrame.setVisible(true);
    }
}

