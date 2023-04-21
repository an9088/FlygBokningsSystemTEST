package controller;

import com.amadeus.exceptions.ResponseException;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import org.xml.sax.SAXException;
import model.AirportCode;
import model.AmadeusAPI;
import model.Booking;
import view.Mainframe;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Mainframe mainframe;

    private Booking booking;

    private String message;

    private AirportCode airportCode;

    private ArrayList<String> flightInfo;

    private ArrayList<String> flightDisplay;


    public Controller() {
        try {
            UIManager.setLookAndFeel(new FlatOneDarkIJTheme());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
        String returnYear = mainframe.getReturnYear().getValue().toString();
        String returnMonth = mainframe.getReturnMonth().getValue().toString();
        String returnDay = mainframe.getReturnDay().getValue().toString();
        String returnDate = returnYear + "-" + returnMonth + "-" + returnDay;
        String date = year + "-" + month + "-" + day;

        //AmadeusAPI converter = new AmadeusAPI(departureAirport, destinationAirport);
        ///////////////DEPARTURE DATES///////////////////

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

        ///////////////RETURN DATES///////////////////

        if (Integer.parseInt(returnMonth) < 10 && Integer.parseInt(returnDay) >= 10) {
            returnDate = year + "-0" + month + "-" + day;
            System.out.println("Test 2 " + returnDate);
        }
        if (Integer.parseInt(returnMonth) >= 10 && Integer.parseInt(returnDay) < 10) {
            returnDate = year + "-" + month + "-0" + day;
            System.out.println("Test 1 " + returnDate);
        }
        if (Integer.parseInt(returnMonth) >= 10 && Integer.parseInt(returnDay) >= 10) {
            returnDate = year + "-" + month + "-" + day;
            System.out.println("Test 3 " + returnDate);
        }
        if (Integer.parseInt(returnMonth) < 10 && Integer.parseInt(returnDay) < 10) {
            returnDate = year + "-0" + month + "-0" + day;
            System.out.println("Test 4 " + returnDate);
        }


        if (departureAirport.equals("")) {
           // mainframe.getEditorPane1().setText("");
            message = "Please enter valid departure destination";
            mainframe.errorMessage(message);
        } else if (destinationAirport.equals("")) {
            //mainframe.getEditorPane1().setText("");
            message = "Please enter valid arrival destination";
            mainframe.errorMessage(message);
        } else if (destinationAirport.equals("") && departureAirport.equals("")) {
          //  mainframe.getEditorPane1().setText("");
            message = "Please enter valid destinations";
            mainframe.errorMessage(message);
        } else if (mainframe.getOneWayTicketOnlyCheckBox().isSelected()){
            AmadeusAPI oneWayTicket = new AmadeusAPI(departureAirport, destinationAirport, date, nbrOfPassengers, this);
            // API apiMessage = new API(departureAirport, destinationAirport, date, this);
            //FakeAPI fake = new FakeAPI(departureAirport, destinationAirport, date, this);
        } else {
            AmadeusAPI returnTicket = new AmadeusAPI(departureAirport, destinationAirport, date, returnDate, nbrOfPassengers, this);
        }

    }

    public void setDisplayMessage(ArrayList<String> message, ArrayList<String> flightDisplay) {

        this.flightDisplay = flightDisplay;
        mainframe.setDisplayMessage(message); // JLIST
    }


    public void createNewBooking(String name, String lastName, String address, String city, String zip, String country, String email, String bookingDetails, int bookingNumber) {

        booking = new Booking(name, lastName, address, city, zip, country, email, bookingNumber, bookingDetails, this);
        System.out.println(booking.toString());

    }



    public void showBookingConfirmation(String bookingMessage) {
        mainframe.showBookingConfirmation(bookingMessage);
    }

    public ArrayList<String> getFlightDisplay() {
        return flightDisplay;
    }
}

