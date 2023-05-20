package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Booking_History_Page {
    private JPanel mainPanel;
    private JList<String> pastBookingsList;
    private JEditorPane bookingDetails;

    private JPanel menu;
    private JLabel userTitle;
    private JPanel bookingHistoryPanel;

    private DefaultListModel<String> bookingListModel;

    public Booking_History_Page() {
        initializeUI();
        bookingDetails.setEditable(false);

        Border detailsBorder = new LineBorder(Color.GRAY);
        detailsBorder = BorderFactory.createTitledBorder("Booking Details");
        bookingDetails.setBorder(detailsBorder);

        Border listBorder = new LineBorder(Color.GRAY);
        listBorder = BorderFactory.createTitledBorder("Booking History");
        pastBookingsList.setBorder(listBorder);
    }

    private void initializeUI() {
        bookingListModel = new DefaultListModel<>(); // Initialize the bookingListModel
        pastBookingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pastBookingsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = pastBookingsList.getSelectedIndex();
                    if (selectedIndex >= 0 && selectedIndex < bookingListModel.getSize()) {
                        String selectedBooking = pastBookingsList.getSelectedValue();
                        String bookingDetailsText = getBookingDetailsText(selectedBooking);
                        bookingDetails.setText(bookingDetailsText);
                    }
                }
            }
        });


    }






    public void showWindow() {
        JFrame frame = new JFrame();
        Font font = new Font("Arial", Font.BOLD, 16); // Create a new font with desired size and boldness
        String title = "<html><body><b><font size='5' color='#FFFFFF'>Booking History</font></b></body></html>"; // HTML formatted title with white color
        frame.setTitle(title); // Set the HTML formatted title
        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(920, 600));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void setUserTitle(String title) {
        userTitle.setText(title);
    }

    public void loadBookingsFromFile(String fileName) {
        DefaultListModel<String> bookingListModel = new DefaultListModel<>();
        List<List<String>> bookingDetailsList = new ArrayList<>();
        String currentBookingTitle = "";
        List<String> currentBookingDetails = new ArrayList<>();
        boolean isBookingSection = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">>> [BookingID: ")) {
                    // Start a new booking section
                    if (!currentBookingTitle.isEmpty()) {
                        // Add previous booking details to the list
                        bookingDetailsList.add(currentBookingDetails);
                    }
                    currentBookingTitle = line.substring(4, line.length() - 4);
                    bookingListModel.addElement(currentBookingTitle);
                    currentBookingDetails = new ArrayList<>();
                    isBookingSection = true;
                } else if (line.equals("=== END OF BOOKING ===")) {
                    // End of current booking section
                    isBookingSection = false;
                } else if (isBookingSection) {
                    // Add the line to the current booking details
                    currentBookingDetails.add(line);
                }
            }

            // Add the last booking details to the list
            if (!currentBookingTitle.isEmpty()) {
                bookingDetailsList.add(currentBookingDetails);
            }
        } catch (IOException e) {
            System.out.println("Failed to load bookings from file: " + e.getMessage());
            // Handle the exception by setting an empty model
            pastBookingsList.setModel(new DefaultListModel<>());
            bookingDetails.setText("");
            return;
        }

        pastBookingsList.setModel(bookingListModel);
        bookingDetails.setText("");

        pastBookingsList.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedIndex = pastBookingsList.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < bookingDetailsList.size()) {
                    List<String> detailsText = bookingDetailsList.get(selectedIndex);
                    String formattedDetailsText = String.join("\n", detailsText);
                    bookingDetails.setText(formattedDetailsText);
                }
            }
        });
    }






    private String getBookingDetailsText(String booking) {
        String[] parts = booking.split(" - ");
        if (parts.length == 2) {
            String details = parts[1];
            return details;
        }
        return "";
    }













}

