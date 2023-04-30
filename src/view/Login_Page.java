package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login_Page {
    private JPanel mainPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JCheckBox rememberMeCheckBox;
    private JLabel forgetPassWordLabelNOTUSED;
    private JButton logInButton;
    private JLabel passwordHideButton;

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



    }

    public static void main(String[] args) {
        Login_Page login = new Login_Page();
        JFrame frame = new JFrame("Sign Up Page");
        frame.setContentPane(login.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
