package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SignUp_Page {
    private JTextField emailField;
    private JTextField surnameField;
    private JTextField forenameField;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JLabel LoginButton;
    private JPanel mainPanel;
    private JLabel passwordHideButton;

    private boolean isSignedUp;

    public SignUp_Page() {

        JFrame frame = new JFrame("Sign Up Page");
        frame.setContentPane(mainPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        signUpButton.setBackground(new Color(0,123,255));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setText("Sign Up");


        LoginButton.setForeground(Color.BLUE);
        LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        passwordField1.setEchoChar((char)0);
        passwordHideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        passwordHideButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (passwordField1.getEchoChar() == (char) 0) {
                    passwordField1.setEchoChar('*');
                } else {
                    passwordField1.setEchoChar((char) 0);
                }
            }
        });


        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your registration logic here
                String firstName = forenameField.getText().trim();
                String lastName = surnameField.getText().trim();
                //String address = addressField.getText().trim();
                //String zip = zipField.getText().trim();
                //String country = countryField.getText().trim();
                //String city = cityField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField1.getPassword()).trim();


                // Check that all fields are filled
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled in.");
                    return;
                }

                // Check that the email contains the @ symbol
                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
                    return;
                }

                // Check that the password meets the requirements
                if (!isPasswordValid(password)) {
                    return;
                }


                // Check if the email is already registered
                try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("Email: " + email)) {
                            JOptionPane.showMessageDialog(null, "This email is already registered.");
                            return;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error checking user data: " + ex.getMessage());
                    return;
                }

                // Save the user data to a text file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                    writer.write("Firstname: " + firstName);
                    writer.newLine();
                    writer.write("Lastname: " + lastName);
                    writer.newLine();
                    writer.write("Email: " + email);
                    writer.newLine();
                    writer.write("Password: " + password);
                    writer.newLine();
                    writer.newLine();

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving user data: " + ex.getMessage());
                    return;
                }

                JOptionPane.showMessageDialog(null, "Registration Successful!");
                frame.dispose();
            }
        });

        LoginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Login_Page();
                frame.dispose();
            }
        });

    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long.");
            return false;
        }

        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
                break;
            }
        }

        if (!hasSpecialCharacter) {
            JOptionPane.showMessageDialog(null, "Password must contain at least one special character.");
            return false;
        }

        return true;
    }
}






