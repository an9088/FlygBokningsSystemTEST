package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login_Page {
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JCheckBox rememberMeCheckBox;
    private JLabel forgetPassWordLabelNOTUSED;
    private JButton logInButton;
    private JLabel passwordHideButton;

    private Mainframe mainframe;

    private static final String DB_FILENAME = "accounts.txt";

    private boolean isLoggedIn;


    public Login_Page(){

        JFrame frame = new JFrame("Login Page");
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        logInButton.setBackground(new Color(0,123,255));
        logInButton.setForeground(Color.WHITE);

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



        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] password = passwordField1.getPassword();

                if (!validateEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid email address.");
                    return;
                }

                if (authenticate(email, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect email or password.");
                }
            }
        });
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }


    private boolean authenticate(String email, char[] password) {
        try {
            Scanner scanner = new Scanner(new File(DB_FILENAME));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length >= 2 && fields[0].trim().equalsIgnoreCase(email.trim())
                        && fields[1].trim().equals(new String(password))) {
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




    public String getEmail() {
        return emailField.getText();
    }


}
