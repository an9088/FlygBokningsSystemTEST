package controller;

import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import model.AmadeusAPI;
import model.Booking;
import view.BookingHistoryGUI;
import view.Mainframe;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class is the main link between the model classes and the view classes. The Controller class is
 * mainly responsible for handling all the logic in the system and passes on information between the view
 * and model classes.
 * @author Dino Patarcec
 * @author Mattias Malm
 * @author Mehdi Muhebbi
 * @author Ellyas Rahimy
 */

public class Controller {

    private boolean paymentSuccessful;
    private Mainframe mainframe;

    private Booking booking;

    private String message, date, returnDate;

    private ArrayList<String> flightInfo;

    private ArrayList<String> flightDisplay;

    private AmadeusAPI amadeus;


    private BookingHistoryGUI bookingHistoryGUI;


    /**
     * In this constructor the colour theme is set for the GUI. It's also here the mainframe is initialized.
     */


    public Controller() {
        try {
            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainframe = new Mainframe(this);
    }

    /**
     * In this method, all the necessary information is taken from the mainframe class to be used in the
     * process of searching for different flights.
     */

    public void searchAvailableFlights() {

        String departureAirport = mainframe.getFromAirport().getText();
        String destinationAirport = mainframe.getToAirport().getText();

        int nbrOfPassengers = (int) mainframe.getSpinnerAdult().getValue();

        String year = mainframe.getYear().getValue().toString();
        String month = mainframe.getMonth().getValue().toString();
        String day = mainframe.getDay().getValue().toString();

        String returnYear = mainframe.getReturnYear().getValue().toString();
        String returnMonth = mainframe.getReturnMonth().getValue().toString();
        String returnDay = mainframe.getReturnDay().getValue().toString();

        date = formatDates(year, month, day);
        returnDate = formatDates(returnYear, returnMonth, returnDay);

        System.out.println("Departure: " + date + "\nReturn date: " + returnDate);


        if (departureAirport.equals("") || departureAirport.equals("Enter Departure City")) {
            message = "Please enter valid departure destination";
            mainframe.errorMessage(message);

        } else if (destinationAirport.equals("") || destinationAirport.equals("Enter Destination City")) {
            message = "Please enter valid arrival destination";
            mainframe.errorMessage(message);

        } else if (departureAirport.equals("") && destinationAirport.equals("")) {
            message = "Please enter valid destinations";
            mainframe.errorMessage(message);

        } else if (mainframe.getOneWayTicketOnlyCheckBox().isSelected()) {

            amadeus = new AmadeusAPI(departureAirport, destinationAirport, date, nbrOfPassengers, this);
            mainframe.getFromAirport().setText("");
            mainframe.getToAirport().setText("");
        } else {

            amadeus = new AmadeusAPI(departureAirport, destinationAirport, date, returnDate, nbrOfPassengers, this);
            mainframe.getFromAirport().setText("");
            mainframe.getToAirport().setText("");
        }

    }

    /**
     * This method is used to pass on the information retrieved from the API to be displayed for the user.
     * @param message - the information to be shown in the JList containing the available flights.
     * @param flightDisplay - this parameter is saved in the instance variable of the same name to be used
     *                      when displaying information about each individual flight.
     */

    public void setDisplayMessage(ArrayList<String> message, ArrayList<String> flightDisplay) {

        this.flightDisplay = flightDisplay;
        mainframe.setDisplayMessage(message); // JLIST
    }

    /**
     * this method is called from the PaymentPage class when a booking is about to be finished
     * @param fullName - name and last name of the user.
     * @param address - the users address.
     * @param city - the hometown of the user.
     * @param zip - the zip code of the user.
     * @param country - the home country of the user.
     * @param email - the email address of the user.
     * @param bookingDetails - the booking details.
     * @param airport - the destination of the trip.
     * @param bookingNumber - the specific booking number.
     */
    public void createNewBooking(String fullName, String address, String city,
                                 String zip, String country, String email, String bookingDetails, String airport, int bookingNumber) {

        Booking booking = new Booking(fullName, address, city, zip, country, email, bookingNumber, bookingDetails, airport, this);

    }

    /**
     * This method passes on the bookingConfirmation sent från the AmadeusAPI class. When a booking has been
     * confirmed, this message will appear as a pop up screen in the application.
     * @param bookingMessage the booking confirmation.
     */
    public void showBookingConfirmation(String bookingMessage) {
        mainframe.showBookingConfirmation(bookingMessage);
    }

    public ArrayList<String> getFlightDisplay() {
        return flightDisplay;
    }

    /**
     * Whenever an error occurs, this message is called. It simply shows whatever error message being passed
     * in to it.
     * @param message the error message.
     */
    public void errorCode(String message) {
        mainframe.errorMessage(message);
    }

    /**
     * This method is used to create the correct format (YYYY-MM-DD) of the dates needed to search for flights.
     * The JSpinners, where the dates are retrieved from in the searchAvailableFlights-method,
     * do not return the correct format by themselves.
     * @param year - String containing the year.
     * @param month - String containing the month.
     * @param day - String containing the day.
     * @return the combined date in the correct format YYYY-MM-DD.
     */
    public String formatDates(String year, String month, String day) {

        String date = year + "-" + month + "-" + day;

        ///////////////DEPARTURE DATES///////////////////

        if (Integer.parseInt(month) < 10 && Integer.parseInt(day) >= 10) {
            date = year + "-0" + month + "-" + day;
        }
        if (Integer.parseInt(month) >= 10 && Integer.parseInt(day) < 10) {
            date = year + "-" + month + "-0" + day;
        }
        if (Integer.parseInt(month) >= 10 && Integer.parseInt(day) >= 10) {
            date = year + "-" + month + "-" + day;
        }
        if (Integer.parseInt(month) < 10 && Integer.parseInt(day) < 10) {
            date = year + "-0" + month + "-0" + day;
        }

        return date;
    }


    public String getAirport() {
        String airport = amadeus.getDestinationCity();
        return airport;
    }

    public String getSignedInEmail() {
      return mainframe.getSignedInEmail();
    }

    /**
     * This is used to create a new booking object for each new booking made by a guest user.
     * @param name First name.
     * @param lastName Last name.
     * @param address Address of the guest user.
     * @param city City of the guest user.
     * @param zip Zip code of the guest user.
     * @param country country code of the guest user.
     * @param email Email address of the guest user.
     * @param bookingDetails The booking confirmation.
     * @param airport the arrival destination of the trip.
     * @param bookingNumber the specified booking number.
     */
    public void createNewGuestBooking(String name, String lastName, String address, String city, String zip, String country,
                                      String email, String bookingDetails, String airport, int bookingNumber) {
        booking = new Booking(name, lastName, address, city, zip, country, email, bookingNumber, bookingDetails, airport, this);

    }

    public boolean processPayment(String name, String email, String cardNumber, String nameOnCard, String expiryDate, String cvvCode, boolean isAmexCard, boolean isMasterCardVisa) {
        // Check if either Mastercard/Visa or American Express is selected
        if (!isMasterCardVisa && !isAmexCard) {
            JOptionPane.showMessageDialog(null, "Please select either Mastercard/Visa or American Express");
            return false;
        }

        // Perform payment processing logic here
        // Example: Validate inputs, make API calls, update model, etc.

        // Validate email
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Payment Failed: Invalid email");
            return false;
        }

        // Validate card number length
        if (isAmexCard && cardNumber.length() != 15) {
            JOptionPane.showMessageDialog(null, "Payment Failed: American Express card number should be 15 digits long");
            return false;
        } else if (isMasterCardVisa && cardNumber.length() != 16) {
            JOptionPane.showMessageDialog(null, "Payment Failed: Mastercard/Visa card number should be 16 digits long");
            return false;
        }

        // Validate expiry date format and validity
        if (!isValidExpiryDate(expiryDate)) {
            JOptionPane.showMessageDialog(null, "Payment Failed: Invalid expiry date");
            return false;
        }

        // Validate CVV code length
        if (isAmexCard && cvvCode.length() != 4) {
            JOptionPane.showMessageDialog(null, "Payment Failed: American Express CVV code should be 4 digits long");
            return false;
        } else if (isMasterCardVisa && cvvCode.length() != 3) {
            JOptionPane.showMessageDialog(null, "Payment Failed: Mastercard/Visa CVV code should be 3 digits long");
            return false;
        }

        // All checks passed, process the payment
        JOptionPane.showMessageDialog(null, "Payment Successful");
        return true;
    }






    private boolean isValidEmail(String email) {
        // Basic email validation by checking for the presence of '@'
        return email.contains("@");
    }

    private boolean isValidExpiryDate(String expiryDate) {
        // Validate expiry date format (MM/YY)
        if (expiryDate.length() != 5 || expiryDate.charAt(2) != '/') {
            return false;
        }

        // Validate month and year values
        String[] parts = expiryDate.split("/");
        int month, year;
        try {
            month = Integer.parseInt(parts[0]);
            year = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        // Validate month and year ranges
        if (month < 1 || month > 12 || year < 0) {
            return false;
        }

        // Additional validation for expiry date in the future
        // You can customize this logic based on your requirements

        return true;
    }
}

