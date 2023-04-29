package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUp_Page {
    private JTextField emailField;
    private JTextField surnameField;
    private JTextField forenameField;
    private JPasswordField passwordField1;
    private JButton signUpButton;
    private JLabel LoginButton;
    private JPanel mainPanel;
    private JLabel passwordHideButton;

    public SignUp_Page() {

        JFrame frame = new JFrame("Sign Up Page");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    }


    public static void main(String[] args) {
        SignUp_Page page = new SignUp_Page();
        JFrame frame = new JFrame("Sign Up Page");
        frame.setContentPane(page.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}

