package controller;

import com.amadeus.exceptions.ResponseException;
import com.formdev.flatlaf.intellijthemes.*;
import model.AmadeusAPI;
import model.Booking;
import org.xml.sax.SAXException;
import view.Mainframe;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

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

        booking = new Booking(fullName, address, city, zip, country, email, bookingNumber, bookingDetails, airport, this);
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
}

