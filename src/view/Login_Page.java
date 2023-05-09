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
    private JFrame frame;

    public Login_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
        frame = new JFrame("Login Page");
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
                login();
            }
        });
    }

    public void login() {
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
                        JOptionPane.showMessageDialog(null, "Login successful, welcome dear Customer!");
                        frame.dispose();
                        updateMenu(email); // pass the email here
                        emailField.setText("");
                        passwordField1.setText("");
                        return;
                    } else {
                        foundEmail = false;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Incorrect email or password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
