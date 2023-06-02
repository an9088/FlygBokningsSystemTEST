package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for deleting bookings from a file.
 * Provides methods to load bookings, retrieve booking details,
 * remove bookings, and update file contents.
 *
 * @author Ellyas Rahimy
 */
public class DeleteBooking {

    private String fileName;

    /**
     * Constructs a new DeleteBooking instance with the specified file name.
     *
     * @param filename The name of the file to work with.
     */
    public DeleteBooking(String filename) {
        this.fileName = filename;
    }


    /**
     * Loads the bookings from the file and returns them as a list of formatted strings.
     *
     * @return The list of bookings loaded from the file.
     */
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

    /**
     * Retrieves the booking details text from the formatted booking string.
     *
     * @param booking The formatted booking string.
     * @return The booking details text.
     */
    public String getBookingDetailsText(String booking) {
        String[] parts = booking.split(" - ");
        if (parts.length == 2) {
            return parts[1];
        }
        return "";
    }

    /**
     * Removes the specified booking from the file.
     *
     * @param booking The booking to be removed.
     */
    public void removeBooking(String booking) {
        String[] parts = booking.split(" - ");
        if (parts.length == 2) {
            String bookingId = parts[0];
            List<String> bookings = loadBookingsFile();
            bookings.removeIf(b -> b.startsWith(bookingId));
            updateFileContents(bookings);
        }
    }

    /**
     * Formats the list of booking details into a single string.
     *
     * @param bookingDetails The list of booking details.
     * @return The formatted booking details string.
     */
    private String formatBookingDetails(List<String> bookingDetails) {
        StringBuilder sb = new StringBuilder();
        for (String line : bookingDetails) {
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }


    /**
     * Updates the contents of the file with the provided list of bookings.
     *
     * @param bookings The list of bookings to update the file contents with.
     */
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
