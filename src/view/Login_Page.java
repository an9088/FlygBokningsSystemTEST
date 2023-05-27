package view;

import model.UserAuthentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents the login page of the application.
 * The Login_Page class provides functionality for user authentication and login.
 * It allows users to enter their email and password to access the application.
 * Upon successful login, the user is greeted with a welcome message and the main menu is updated.
 * The Login_Page class interacts with the UserAuthentication class to validate user credentials.
 * It also handles UI elements such as text fields, buttons, and labels for the login page.
 * The class is designed to be used in conjunction with the Mainframe class to provide a complete user login experience.
 *
 * @author Mehdi Muhebbi
 * @author Dino Patarcec
 */
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

    private UserAuthentication userAuth;

    /**
     * Constructs a new Login_Page object.
     *
     * @param mainframe the mainframe of the application
     */
    public Login_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
        this.userAuth = new UserAuthentication();
        frame = new JFrame();
        setupFrame();
        setupListeners();
    }

    /**
     * Sets up the frame and visual elements of the login page.
     */
    private void setupFrame() {
        Font font = new Font("Arial", Font.BOLD, 16);
        String title = "<html><body><b><font size='5' color='#FFFFFF'>Login</font></b></body></html>"; // HTML formatted title with white color
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

    /**
     * Sets up the listeners for interactive elements on the login page.
     */
    private void setupListeners() {
        passwordHideButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                guiUtils.togglePasswordVisibility(passwordField);
            }
        });

        logInButton.addActionListener(e -> attemptLogin());
    }



    /**
     * Attempts to perform a login based on the entered credentials.
     * Validates the input fields and checks the user's authentication.
     */
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

    /**
     * Performs the login by validating the user's credentials.
     * If the login is successful, it calls the `loginSuccessful` method.
     */
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

    /**
     * Handles the actions to be taken upon successful login.
     * Displays a success message, updates the menu, and resets the input fields.
     *
     * @param email the email of the logged-in user
     */
    private void loginSuccessful(String email) {
        JOptionPane.showMessageDialog(null, "Login successful, welcome dear Customer!");
        frame.dispose();
        updateMenu(email);
        resetFields();
    }

    /**
     * Resets the input fields to their initial state.
     */
    private void resetFields() {
        emailField.setText("");
        passwordField.setText("");
    }

    /**
     * Updates the user-specific menu options based on the logged-in user's email.
     *
     * @param email the email of the logged-in user
     */
    public void updateMenu(String email) {
        mainframe.setMenu1Text(email);
        email = getEmail();
        setupUserMenu();
    }

    /**
     * Sets up the user-specific menu options.
     */
    private void setupUserMenu() {
        JButton userButton = mainframe.getUserButton();
        JPopupMenu popupMenu = createPopupMenu();

        userButton.setComponentPopupMenu(popupMenu);
        mainframe.addMenuItemToMenu1(createMenuItem("My Bookings"));
        mainframe.addMenuItemToMenu1(createMenuItem("Sign out", this::signOut));
    }

    /**
     * Performs the sign out action and updates the mainframe.
     *
     * @param e the action event
     */
    private void signOut(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Sign Out clicked");
        mainframe.setupMenu();
        mainframe.setExtendedState(JFrame.NORMAL);
        mainframe.setUndecorated(false);
        mainframe.revalidate();
        mainframe.repaint();
    }

    /**
     * Creates a menu item with the specified title and action listener.
     *
     * @param title    the title of the menu item
     * @return the created menu item
     */
    private JMenuItem createMenuItem(String title) {
        if (title.equals("My Bookings")) {
            return createMenuItem(title, e -> {
                BookingHistoryGUI bookingPage = new BookingHistoryGUI();
                bookingPage.showWindow();
                bookingPage.setUserTitle(email);
                bookingPage.loadBookingsFromFile( email + ".txt");
            });
        } else {
            return createMenuItem(title, e -> JOptionPane.showMessageDialog(null, title + " clicked"));
        }
    }


    /**
     * Creates a menu item with the specified title and action listener.
     *
     * @param title    the title of the menu item
     * @param listener the action listener for the menu item
     * @return the created menu item
     */
    private JMenuItem createMenuItem(String title, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    /**
     * Creates an empty popup menu.
     *
     * @return the created popup menu
     */
    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        return popupMenu;
    }

    /**
     * Retrieves the entered email from the email field.
     *
     * @return the entered email
     */
    public String getEmail() {
        return emailField.getText();
    }


    /**
     * Retrieves the entered password from the password field.
     *
     * @return the entered password
     */
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
