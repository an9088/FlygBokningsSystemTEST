package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Login_Page {
    private static final String USERS_FILE_PATH = "users.txt";
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField;

    private JCheckBox rememberMeCheckBox;

    private JLabel forgetPassWordLabelNOTUSED;
    private JButton logInButton;
    private JLabel passwordHideButton;

    private Mainframe mainframe;
    private JFrame frame;

    public Login_Page(Mainframe mainframe) {
        this.mainframe = mainframe;
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
        if (!validateEmail(getEmail())) {
            JOptionPane.showMessageDialog(null, "Email is not valid");
            return;
        }
        login();
    }

    public void login() {
        String email = getEmail();
        String password = getPassword();

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
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
                        loginSuccessful(email);
                        return;
                    } else {
                        foundEmail = false;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Incorrect email or password");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to read users data");
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
        setupUserMenu();
    }

    private void setupUserMenu() {
        JButton userButton = mainframe.getUserButton();
        JPopupMenu popupMenu = createPopupMenu();

        userButton.setComponentPopupMenu(popupMenu);
        mainframe.addMenuItemToMenu1(createMenuItem("My Bookings"));
        mainframe.addMenuItemToMenu1(createMenuItem("Change information"));
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
        return createMenuItem(title, e -> JOptionPane.showMessageDialog(null, title + " clicked"));
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

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
