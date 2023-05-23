package controller;

import com.amadeus.exceptions.ResponseException;
import com.formdev.flatlaf.intellijthemes.*;
import model.AmadeusAPI;
import model.Booking;
import org.xml.sax.SAXException;
import view.Booking_History_Page;
import view.Mainframe;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private boolean paymentSuccessful;
    private Mainframe mainframe;

    private Booking booking;

    private String message, date, returnDate;

    private ArrayList<String> flightInfo;

    private ArrayList<String> flightDisplay;

    private AmadeusAPI amadeus;


    public Controller() {
        try {
            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainframe = new Mainframe(this);
    }

    public void searchAvailableFlights() throws IOException, InterruptedException, ParserConfigurationException, SAXException, ResponseException {


        String departureAirport = mainframe.getFromAirport().getText();
        String destinationAirport = mainframe.getToAirport().getText();

        int nbrOfPassengers = (int) mainframe.getSpinnerAdult().getValue();

        date = getDepartureDate();
        returnDate = getReturnDate();

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

    public void setDisplayMessage(ArrayList<String> message, ArrayList<String> flightDisplay) {

        this.flightDisplay = flightDisplay;
        mainframe.setDisplayMessage(message); // JLIST
    }


    public void createNewBooking(String fullName, String address, String city,
                                 String zip, String country, String email, String bookingDetails, String airport, int bookingNumber) {

        Booking booking = new Booking(fullName, address, city, zip, country, email, bookingNumber, bookingDetails, airport, this);





    }


    public void showBookingConfirmation(String bookingMessage) {
        mainframe.showBookingConfirmation(bookingMessage);
    }

    public ArrayList<String> getFlightDisplay() {
        return flightDisplay;
    }

    public void errorCode(String message) {
        mainframe.errorMessage(message);
    }

    public String getDepartureDate() {

        String year = mainframe.getYear().getValue().toString();
        String month = mainframe.getMonth().getValue().toString();
        String day = mainframe.getDay().getValue().toString();

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

    public String getReturnDate() {

        ///////////////RETURN DATES///////////////////

        String returnYear = mainframe.getReturnYear().getValue().toString();
        String returnMonth = mainframe.getReturnMonth().getValue().toString();
        String returnDay = mainframe.getReturnDay().getValue().toString();

        String returnDate = returnYear + "-" + returnMonth + "-" + returnDay;

        if (Integer.parseInt(returnMonth) < 10 && Integer.parseInt(returnDay) >= 10) {
            returnDate = returnYear + "-0" + returnMonth + "-" + returnDay;
        }
        if (Integer.parseInt(returnMonth) >= 10 && Integer.parseInt(returnDay) < 10) {
            returnDate = returnYear + "-" + returnMonth + "-0" + returnDay;
        }
        if (Integer.parseInt(returnMonth) >= 10 && Integer.parseInt(returnDay) >= 10) {
            returnDate = returnYear + "-" + returnMonth + "-" + returnDay;
        }
        if (Integer.parseInt(returnMonth) < 10 && Integer.parseInt(returnDay) < 10) {
            returnDate = returnYear + "-0" + returnMonth + "-0" + returnDay;
        }

        return returnDate;
    }


    public String getAirport() {
        String airport = amadeus.getDestinationAirport();
        return airport;
    }

    public String getSignedInEmail() {
      return mainframe.getSignedInEmail();
    }

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

