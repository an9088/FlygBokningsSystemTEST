package view;

import model.UserAuthentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Login_Page {
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField;

    private JCheckBox rememberMeCheckBox;

    private JLabel forgetPassWordLabelNOTUSED;
    private JButton logInButton;
    private JLabel passwordHideButton;

    private Mainframe mainframe;
    private JFrame frame;

    private String email;

    private String password;

    // Add UserAuthentication as a field
    private UserAuthentication userAuth;

    public Login_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
        this.userAuth = new UserAuthentication();
        frame = new JFrame();
        setupFrame();
        setupListeners();
    }

    private void setupFrame() {
        Font font = new Font("Arial", Font.BOLD, 16);
        String title = "<html><body><b><font size='5' color='#FFFFFF'>Login</font></b></body></html>";
        frame.setTitle(title);
        frame.setContentPane(mainPanel);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        logInButton.setBackground(new Color(0, 123, 255));
        logInButton.setForeground(Color.WHITE);
        passwordField.setEchoChar((char) 0);
        passwordHideButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setupListeners() {
        passwordHideButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                togglePasswordVisibility();
            }
        });

        logInButton.addActionListener(e -> attemptLogin());
    }

    private void togglePasswordVisibility() {
        if (passwordField.getEchoChar() == (char) 0) {
            passwordField.setEchoChar('*');
        } else {
            passwordField.setEchoChar((char) 0);
        }
    }

    private void attemptLogin() {
        if (emailField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "All fields are required to login.");
            return;
        }
        if (!userAuth.validateEmail(getEmail())) {
            JOptionPane.showMessageDialog(null, "Email is not valid");
            return;
        }
        login();
    }

    public void login() {
         email = getEmail();
         password = getPassword();

        // Use UserAuthentication to validate user
        if(userAuth.validateUser(email, password)) {
            loginSuccessful(email);
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect email or password");
        }
    }

    private void loginSuccessful(String email) {
        JOptionPane.showMessageDialog(null, "Login successful, welcome dear Customer!");
        frame.dispose();
        updateMenu(email);
        resetFields();
    }

    private void resetFields() {
        emailField.setText("");
        passwordField.setText("");
    }

    public void updateMenu(String email) {
        mainframe.setMenu1Text(email);
        email = getEmail();
        setupUserMenu();
    }

    private void setupUserMenu() {
        JButton userButton = mainframe.getUserButton();
        JPopupMenu popupMenu = createPopupMenu();

        userButton.setComponentPopupMenu(popupMenu);
        mainframe.addMenuItemToMenu1(createMenuItem("My Bookings"));
        mainframe.addMenuItemToMenu1(createMenuItem("Sign out", this::signOut));
    }

    private void signOut(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Sign Out clicked");
        mainframe.setupMenu();
        mainframe.setExtendedState(JFrame.NORMAL);
        mainframe.setUndecorated(false);
        mainframe.revalidate();
        mainframe.repaint();
    }

    private JMenuItem createMenuItem(String title) {
        if (title.equals("My Bookings")) {
            // Do something when the title is "My Bookings"
            return createMenuItem(title, e -> {
                Booking_History_Page bookingPage = new Booking_History_Page();
                bookingPage.showWindow();
                bookingPage.setUserTitle(email);
                bookingPage.loadBookingsFromFile( email + ".txt");
            });
        } else {
            return createMenuItem(title, e -> JOptionPane.showMessageDialog(null, title + " clicked"));
        }
    }


    private JMenuItem createMenuItem(String title, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        return popupMenu;
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
