package controller;

import com.amadeus.exceptions.ResponseException;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
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

    private String message;

    private ArrayList<String> flightInfo;

    private ArrayList<String> flightDisplay;


    public Controller() {
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mainframe = new Mainframe(this);
    }


    public void searchAvailableFlights() throws IOException, InterruptedException, ParserConfigurationException, SAXException, ResponseException {


        String departureAirport = mainframe.getFromAirport().getText();
        String destinationAirport = mainframe.getToAirport().getText();

        int nbrOfPassengers = (int) mainframe.getSpinnerAdult().getValue();

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


        if (departureAirport.equals("")) {
            message = "Please enter valid departure destination";
            mainframe.errorMessage(message);

        } else if (destinationAirport.equals("")) {
            message = "Please enter valid arrival destination";
            mainframe.errorMessage(message);

        } else if (destinationAirport.equals("") && departureAirport.equals("")) {
            message = "Please enter valid destinations";
            mainframe.errorMessage(message);

        } else if (mainframe.getOneWayTicketOnlyCheckBox().isSelected()) {
            AmadeusAPI oneWayTicket = new AmadeusAPI(departureAirport, destinationAirport, date, nbrOfPassengers, this);

            mainframe.getFromAirport().setText("Enter Departure City");
            mainframe.getToAirport().setText("Enter Destination City");

        } else {
            AmadeusAPI returnTicket = new AmadeusAPI(departureAirport, destinationAirport, date, returnDate, nbrOfPassengers, this);

            mainframe.getFromAirport().setText("Enter Departure City");
            mainframe.getToAirport().setText("Enter Destination City");
        }

    }

    public void setDisplayMessage(ArrayList<String> message, ArrayList<String> flightDisplay) {

        this.flightDisplay = flightDisplay;
        mainframe.setDisplayMessage(message); // JLIST
    }


    public void createNewBooking(String name, String lastName, String address, String city,
                                 String zip, String country, String email, String bookingDetails, int bookingNumber) {

        booking = new Booking(name, lastName, address, city, zip, country, email, bookingNumber, bookingDetails, this);
        System.out.println(booking.toString());
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
}

