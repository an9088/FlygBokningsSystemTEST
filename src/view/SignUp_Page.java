package view;

import model.UserRegistrationService;
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
    UserRegistrationService userRegistrationService;

    public SignUp_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
        this.userRegistrationService = new UserRegistrationService();
        frame = new JFrame();
        Font font = new Font("Arial", Font.BOLD, 16); // Create a new font with desired size and boldness
        String title = "<html><body><b><font size='5' color='#FFFFFF'>Sign up</font></b></body></html>"; // HTML formatted title with white color
        frame.setTitle(title); // Set the HTML formatted title
        frame.setContentPane(mainPanel);
        frame.setSize(400, 300);
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
        LoginButton.setForeground(new Color(51, 153, 255));
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

    private void signUp() {
        String firstName = forenameField.getText().trim();
        String lastName = surnameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField1.getPassword()).trim();

        if (!userRegistrationService.isInputValid(firstName, lastName, email, password)) {
            return;
        }

        try {
            userRegistrationService.saveUserData(firstName, lastName, email, password);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving user data: " + ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(null, "Registration Successful!");
        updateMenu(email);
        frame.dispose();
    }

    void updateMenu(String email) {
        mainframe.setMenu1Text(email);
        JMenuItem myBookings = new JMenuItem("My Bookings");
        myBookings.addActionListener(e -> {
            Booking_History_Page bookingPage = new Booking_History_Page();
            bookingPage.showWindow();
            bookingPage.setUserTitle(email);
            bookingPage.loadBookingsFromFile(email + ".txt");
        });


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

