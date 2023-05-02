package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
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

    private boolean isSignedUp;

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
                String email = emailField.getText();
                String surname = surnameField.getText();
                String forename = forenameField.getText();
                char[] password = passwordField1.getPassword();

                if (!validateEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid email address.");
                    return;
                }

                if (!validatePassword(password)) {
                    JOptionPane.showMessageDialog(null, "Invalid password. Password must be at least 8 characters long and contain a digit and a special symbol.");
                    return;
                }

                if (emailExists(email)) {
                    JOptionPane.showMessageDialog(null, "An account with this email already exists.");
                    return;
                }

                saveAccount(email, surname, forename, password);
                JOptionPane.showMessageDialog(null, "Account created successfully!");

            }
        });

        LoginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Login_Page();
                frame.dispose();
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


    private boolean emailExists(String email) {
        try {
            Scanner scanner = new Scanner(new File(DB_FILENAME));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length > 0
                        && fields[0].trim().equalsIgnoreCase(email.trim())) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
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

    public String getEmail() {
        return emailField.getText();
    }




}

