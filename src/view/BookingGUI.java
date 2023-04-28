package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingGUI {

    private JFrame bookingsFrame;
    private JPanel bookingsPanel;
    private JTextField emailField;
    private JButton loginButton;
    private JButton bookingsButton;

    public BookingGUI() {
        createGUI();
    }

    private void createGUI() {
        // Set up the main frame
        bookingsFrame = new JFrame("Booking GUI");
       // bookingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookingsFrame.setSize(400, 300);

        // Set up the panel with a grid layout
        JPanel bookingsPanel = new JPanel(new GridBagLayout());
        bookingsPanel.setBackground(Color.PINK);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        bookingsPanel.add(emailLabel, constraints);

        JTextField emailField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        bookingsPanel.add(emailField, constraints);

        bookingsButton = new JButton("Bookings");
        bookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGUI();

            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        bookingsPanel.add(bookingsButton, constraints);


        // Add the panel to the frame and show the frame
        bookingsFrame.getContentPane().add(bookingsPanel);
        bookingsFrame.setLocationRelativeTo(null);
        bookingsFrame.setVisible(true);
    }
}
