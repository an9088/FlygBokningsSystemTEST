package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookingHistoryHandler {
    private List<Booking> bookings;

    public BookingHistoryHandler() {
        bookings = new ArrayList<>();

    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void removeBooking(int index) {
        if (index >= 0 && index < bookings.size()) {
            bookings.remove(index);
        }
    }

    private String generateUniqueBookingId() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        return "Booking-" + timestamp + "-" + randomNumber;
    }

    public String generateBookingTitle(Booking booking) {
        String uniqueId = generateUniqueBookingId();
        String title = "Booking #" + booking.getBookingNumber() + " (" + uniqueId + ") - " + booking.getFullName();
        return title;
    }


}
