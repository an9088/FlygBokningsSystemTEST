package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Login_Page {
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JCheckBox rememberMeCheckBox;
    private JLabel forgetPassWordLabelNOTUSED;
    private JButton logInButton;
    private JLabel passwordHideButton;

    private Mainframe mainframe;
    private boolean isLoggedIn;

    public Login_Page() {
        JFrame frame = new JFrame("Login Page");
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        logInButton.setBackground(new Color(0, 123, 255));
        logInButton.setForeground(Color.WHITE);

        passwordField1.setEchoChar((char) 0);
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

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emailField.getText().isEmpty() || passwordField1.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "All fields are required to login.");
                    return;
                }

                boolean loginSuccessful = login();
                if (loginSuccessful) {
                    JOptionPane.showMessageDialog(null, "Login successful, welcome dear Customer!");
                    emailField.setText("");
                    passwordField1.setText("");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect email or password");
                }
            }
        });

    }

    public boolean login() {
        String email = getEmail();
        String password = getPassword();

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
                        return true;
                    } else {
                        foundEmail = false;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }





    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField1.getPassword());
    }

}
