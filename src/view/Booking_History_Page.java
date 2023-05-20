package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Booking_History_Page {
    private JPanel mainPanel;
    private JList<String> pastBookingsList;
    private JEditorPane bookingDetails;
    private JButton deleteBookingButton;

    private JPanel menu;
    private JLabel userTitle;
    private JPanel bookingHistoryPanel;

    private DefaultListModel<String> bookingListModel;

    public Booking_History_Page() {
        initializeUI();
        bookingDetails.setEditable(false);

        Border detailsBorder = new LineBorder(Color.GRAY);
        bookingDetails.setBorder(detailsBorder);

        Border listBorder = new LineBorder(Color.GRAY);
        pastBookingsList.setBorder(listBorder);
    }

    private void initializeUI() {
        pastBookingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pastBookingsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = pastBookingsList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        String selectedBooking = pastBookingsList.getSelectedValue();
                        String bookingDetailsText = getBookingDetailsText(selectedBooking);
                        bookingDetails.setText(bookingDetailsText);
                    }
                }
            }
        });

        deleteBookingButton.addActionListener(e -> {
            int selectedIndex = pastBookingsList.getSelectedIndex();
            if (selectedIndex >= 0) {
                DefaultListModel<String> model = (DefaultListModel<String>) pastBookingsList.getModel();
                model.remove(selectedIndex);
                bookingDetails.setText("");
            }
        });
    }

    private String getBookingDetailsText(String booking) {
        String[] parts = booking.split(" - ");
        if (parts.length == 2) {
            return parts[1]; // Only display the details, excluding the booking ID
        }
        return "";
    }


    public void showWindow() {
        JFrame frame = new JFrame("Booking History");
        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(920, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public void setUserTitle(String title) {
        userTitle.setText(title);
    }

    public void loadBookingsFromFile(String fileName) {
        DefaultListModel<String> bookingListModel = new DefaultListModel<>();
        StringBuilder bookingDetailsText = new StringBuilder();
        String currentBookingTitle = "";
        boolean isBookingSection = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">>> ")) {
                    // Extract the booking title from the delimiter
                    currentBookingTitle = line.substring(4, line.length() - 4);
                    bookingListModel.addElement(currentBookingTitle);
                    isBookingSection = true;
                } else if (line.equals("=== END OF BOOKING ===")) {
                    isBookingSection = false;
                } else if (isBookingSection) {
                    // Add the booking details to the editor pane
                    bookingDetailsText.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load bookings from file: " + e.getMessage());
        }

        pastBookingsList.setModel(bookingListModel);
        bookingDetails.setText("");

        pastBookingsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = pastBookingsList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        String selectedBooking = pastBookingsList.getSelectedValue();
                        String bookingDetailsText = getBookingDetailsText(selectedBooking);
                        bookingDetails.setText(bookingDetailsText);
                    }
                }
            }
        });
    }









}

