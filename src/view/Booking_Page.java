package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Booking_Page {
    private JPanel mainPanel;
    private JTextField bookingNumberSearch;
    private JPanel bookingPanel;

    public Booking_Page(){

        addSuggestionText(bookingNumberSearch,"Search by booking number");

    }

    private void addSuggestionText(JTextField field, String suggestion) {

        Color gray = Color.GRAY;
        field.setForeground(gray);


        field.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(suggestion)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(suggestion);
                    field.setForeground(gray);
                }
            }
        });


        if (field.getText().isEmpty()) {
            field.setText(suggestion);
        }
    }

    public static void main(String[] args) {

        Booking_Page bookingPage = new Booking_Page();


        JFrame frame = new JFrame("Payment Page");
        frame.setContentPane(bookingPage.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }
}
