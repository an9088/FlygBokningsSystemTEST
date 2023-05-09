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
    private JFrame frame;

    Mainframe mainframe;

    public SignUp_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
        frame = new JFrame("Sign Up Page");
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        setUpUI();

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });

        LoginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Login_Page(mainframe);
                frame.dispose();
            }
        });
    }

    private void setUpUI() {
        signUpButton.setBackground(new Color(0,123,255));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setText("Sign Up");
        LoginButton.setForeground(Color.BLUE);
        LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        passwordField1.setEchoChar((char)0);
        passwordHideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        passwordHideButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                togglePasswordVisibility();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (passwordField1.getEchoChar() == (char) 0) {
            passwordField1.setEchoChar('*');
        } else {
            passwordField1.setEchoChar((char) 0);
        }
    }


        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your registration logic here
                String firstName = forenameField.getText().trim();
                String lastName = surnameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField1.getPassword()).trim();

    private void signUp() {
        // Implement your registration logic here
        String firstName = forenameField.getText().trim();
        String lastName = surnameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField1.getPassword()).trim();


        if (!isInputValid(firstName, lastName, email, password)) {
            return;
        }

        try {
            saveUserData(firstName, lastName, email, password);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving user data: " + ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(null, "Registration Successful!");
        updateMenu(email);
        frame.dispose();
    }

    private boolean isInputValid(String firstName, String lastName, String email, String password) {
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

    private void saveUserData(String firstName, String lastName, String email, String password) throws IOException {
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

    void updateMenu(String email) {
        mainframe.setMenu1Text(email);
        JMenuItem myBookings = new JMenuItem("My Bookings");
        myBookings.addActionListener(e -> JOptionPane.showMessageDialog(null, "My Bookings clicked"));

        JMenuItem chngInfo = new JMenuItem("Change information");
        chngInfo.addActionListener(e -> JOptionPane.showMessageDialog(null, "Change Information clicked"));

        JMenuItem signOut = new JMenuItem("Sign out");
        signOut.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Sign Out clicked");
            mainframe.setupMenu();
        });
        if(mainframe.getMenu1ItemCount() > 0) {
            mainframe.removeMenuItemFromMenu1(0);
        }
        if(mainframe.getMenu1ItemCount() > 0) {
            mainframe.removeMenuItemFromMenu1(0);
        }
        mainframe.addMenuItemToMenu1(myBookings);
        mainframe.addMenuItemToMenu1(chngInfo);
        mainframe.addMenuItemToMenu1(signOut);
    }
}

