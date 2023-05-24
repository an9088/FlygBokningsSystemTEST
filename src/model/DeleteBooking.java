package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteBooking {

    private String fileName;

    public DeleteBooking(String filename) {
        this.fileName = filename;
    }


    public List<String> loadBookingsFile() {
        List<String> bookings = new ArrayList<>();
        List<String> currentBookingDetails = new ArrayList<>();
        boolean isBookingSection = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(">>> [BookingID: ")) {
                    if (!currentBookingDetails.isEmpty()) {
                        String currentBooking = formatBookingDetails(currentBookingDetails);
                        bookings.add(currentBooking);
                    }
                    currentBookingDetails = new ArrayList<>();
                    isBookingSection = true;
                } else if (line.equals("=== END OF BOOKING ===")) {
                    isBookingSection = false;
                } else if (isBookingSection) {
                    currentBookingDetails.add(line);
                }
            }
            if (!currentBookingDetails.isEmpty()) {
                String currentBooking = formatBookingDetails(currentBookingDetails);
                bookings.add(currentBooking);
            }
        } catch (IOException e) {
            System.out.println("Failed to load bookings from file: " + e.getMessage());
        }

        return bookings;
    }

    public String getBookingDetailsText(String booking) {
        String[] parts = booking.split(" - ");
        if (parts.length == 2) {
            return parts[1];
        }
        return "";
    }

    public void removeBooking(String booking) {
        String[] parts = booking.split(" - ");
        if (parts.length == 2) {
            String bookingId = parts[0];
            List<String> bookings = loadBookingsFile();
            bookings.removeIf(b -> b.startsWith(bookingId));
            updateFileContents(bookings);
        }
    }

    private String formatBookingDetails(List<String> bookingDetails) {
        StringBuilder sb = new StringBuilder();
        for (String line : bookingDetails) {
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }

    private void updateFileContents(List<String> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String booking : bookings) {
                writer.write(">>> [BookingID: " + booking.split(" - ")[0] + "]\n");
                writer.write(booking.split(" - ")[1]);
                writer.write("\n=== END OF BOOKING ===\n");
            }
        } catch (IOException ex) {
            System.out.println("Failed to update file contents: " + ex.getMessage());
        }
    }
}
