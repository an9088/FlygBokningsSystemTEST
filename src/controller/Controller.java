package controller;

import com.amadeus.exceptions.ResponseException;
import org.xml.sax.SAXException;
import model.AirportCode;
import model.AmadeusAPI;
import model.Booking;
import view.Mainframe;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Mainframe mainframe;

    private Booking booking;

    private String message;

    private AirportCode airportCode;


    public Controller() {
        mainframe = new Mainframe(this);
        this.airportCode = airportCode;
    }

    public void searchAvailableFlights() throws IOException, InterruptedException, ParserConfigurationException, SAXException, ResponseException {

        String departureAirport = mainframe.getFromAirport().getText();
        String destinationAirport = mainframe.getToAirport().getText();
        int nbrOfPassengers = (int) mainframe.getSpinnerAdult().getValue();
        String year = mainframe.getYear().getValue().toString();
        String month = mainframe.getMonth().getValue().toString();
        String day = mainframe.getDay().getValue().toString();
        String date = year + "-" + month + "-" + day;

        if (Integer.parseInt(month) < 10 && Integer.parseInt(day) >= 10) {
            date = year + "-0" + month + "-" + day;
            System.out.println("Test 2 " + date);
        }
        if (Integer.parseInt(month) >= 10 && Integer.parseInt(day) < 10) {
            date = year + "-" + month + "-0" + day;
            System.out.println("Test 1 " + date);
        }
        if (Integer.parseInt(month) >= 10 && Integer.parseInt(day) >= 10) {
            date = year + "-" + month + "-" + day;
            System.out.println("Test 3 " + date);
        }
        if (Integer.parseInt(month) < 10 && Integer.parseInt(day) < 10) {
            date = year + "-0" + month + "-0" + day;
            System.out.println("Test 4 " + date);
        }


        if (departureAirport.equals("")) {
            mainframe.getEditorPane1().setText("");
            message = "Please enter valid departure destination";
            mainframe.errorMessage(message);
        } else if (destinationAirport.equals("")) {
            mainframe.getEditorPane1().setText("");
            message = "Please enter valid arrival destination";
            mainframe.errorMessage(message);
        } else if (destinationAirport.equals("") && departureAirport.equals("")) {
            mainframe.getEditorPane1().setText("");
            message = "Please enter valid destinations";
            mainframe.errorMessage(message);
        } else {

            // API apiMessage = new API(departureAirport, destinationAirport, date, this);
            //FakeAPI fake = new FakeAPI(departureAirport, destinationAirport, date, this);
            AmadeusAPI am = new AmadeusAPI(departureAirport, destinationAirport, date, nbrOfPassengers, this);
        }

    }

    public void setDisplayMessage(ArrayList message) {
        mainframe.getEditorPane1().setText(String.valueOf(message));
    }


    public void createNewBooking(String name, String lastName, String address, String city, String zip, String country, String email, int bookingNumber) {

        booking = new Booking(name, lastName, address, city, zip, country, email, bookingNumber, this);
        System.out.println(booking.toString());

    }

    public void showBookingConfirmation(String bookingMessage) {
        mainframe.showBookingConfirmation(bookingMessage);
    }
}

