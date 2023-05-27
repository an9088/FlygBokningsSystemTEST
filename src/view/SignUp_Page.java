package view;

import model.UserRegistrationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
 * Represents the sign-up page of the application.
 * Allows users to create a new account by providing their personal information.
 * The sign-up page includes fields for email, surname, forename, and password.
 * Users can sign up, toggle password visibility, and navigate to the login page.
 *
 * @author Mehdi Muhebbi
 * @author Dino Patarcec
 */
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



    /**
     * Constructs a SignUp_Page object.
     *
     * @param mainframe the mainframe of the application
     */
    public SignUp_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
        this.userRegistrationService = new UserRegistrationService();
        frame = new JFrame();
        Font font = new Font("Arial", Font.BOLD, 16);
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

    /**
     * Sets up the UI elements of the sign-up page.
     * Configures the appearance and behavior of the UI components.
     */
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
                guiUtils.togglePasswordVisibility(passwordField1);
            }
        });
    }


    /**
     * Performs the sign-up process.
     * Collects the user's input, validates it, and saves the user's data if valid.
     */
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

    /**
     * Updates the menu options after a successful sign-up.
     *
     * @param email the email address of the newly registered user
     */
    void updateMenu(String email) {
        mainframe.setMenu1Text(email);
        JMenuItem myBookings = new JMenuItem("My Bookings");
        myBookings.addActionListener(e -> {
            BookingHistoryGUI bookingPage = new BookingHistoryGUI();
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

