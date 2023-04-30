package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Page {
    private JTextField emailField;
    private JTextField surnameField;
    private JTextField forenameField;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JLabel LoginButton;
    private JPanel mainPanel;
    private JLabel passwordHideButton;

    private static final String DB_FILENAME = "accounts.txt";
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

        passwordField1.setEchoChar((char)0);


        LoginButton.setForeground(Color.BLUE);
        LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
                String email = emailField.getText();
                String surname = surnameField.getText();
                String forename = forenameField.getText();
                char[] password = passwordField1.getPassword();

                if (validateEmail(email) && validatePassword(password)) {
                    saveAccount(email, surname, forename, password);
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password. Password must be at least 8 characters long and contain a digit and a special symbol.");
                }
            }
        });
    }


    private boolean validatePassword(char[] password) {
        String passwordString = new String(password);

        if (passwordString.length() < 8) {
            return false;
        }

        boolean hasDigit = false;
        boolean hasSpecialSymbol = false;
        for (int i = 0; i < passwordString.length(); i++) {
            char c = passwordString.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialSymbol = true;
            }
        }

        return hasDigit && hasSpecialSymbol;
    }


    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void saveAccount(String email, String surname, String forename, char[] password) {
        try {
            FileWriter writer = new FileWriter(DB_FILENAME, true);
            writer.write(email + "," + new String(password) + "," + surname + "," + forename + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabaseFile() {
        try {
            File file = new File(DB_FILENAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

