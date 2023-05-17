package model;

import javax.swing.*;
import java.io.*;

public class UserRegistrationService {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public boolean isInputValid(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        // Check that all fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled in.");
            return false;
        }

        // Check that the email contains the @ symbol
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
            return false;
        }

        // Check that the password meets the requirements
        if (!isPasswordValid(password)) {
            return false;
        }

        // Check if the email is already registered
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Email: " + email)) {
                    JOptionPane.showMessageDialog(null, "This email is already registered.");
                    return false;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error checking user data: " + ex.getMessage());
            return false;
        }

        return true;
    }

    public void saveUserData(String firstName, String lastName, String email, String password) throws IOException {
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
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            JOptionPane.showMessageDialog(null, "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long!");
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
