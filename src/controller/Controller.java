package src.controller;

import org.xml.sax.SAXException;
import src.model.AirportCode;
import src.model.Booking;
import src.view.BookingCreatorGUI;
import src.view.FakeAPI;
import src.view.Mainframe;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    private Mainframe mainframe;

    private Booking booking;

    private AirportCode airportCode;

    private Scanner scanner = new Scanner(System.in);

    public Controller() {
        mainframe = new Mainframe(this);
        this.airportCode = airportCode;
    }

    public void searchAvailableFlights() throws IOException, InterruptedException, ParserConfigurationException, SAXException {

        String departureAirport = mainframe.getFromAirport().getText();
        String destinationAirport = mainframe.getToAirport().getText();
        String year = mainframe.getYear().getValue().toString();
        String month = mainframe.getMonth().getValue().toString();
        String day = mainframe.getDay().getValue().toString();
        String date = year + "0" + month + "0" + day;
        /*if(month.startsWith("10") || month.startsWith("11") || month.startsWith("12"))
            date = year + month + "0" + day;
        if(!(day.startsWith("1,2,3,4,5,6,7,8,9")))
            date = year + "0"+ month + day;
        if(!(month.startsWith("1,2,3,4,5,6,7,8,9")) && (!(day.startsWith("1,2,3,4,5,6,7,8,9"))))
            date = year + month + day;

         */

       // API apiMessage = new API(departureAirport, destinationAirport, date, this);
        FakeAPI fake = new FakeAPI(departureAirport, destinationAirport, date, this);

    }

    public void setDisplayMessage(ArrayList message) {
        mainframe.getEditorPane1().setText(String.valueOf(message));
    }


    public void createNewBooking(String name, String lastName, String address, String city, String zip, String country, String email) {

        booking = new Booking(name, lastName, address, city, zip, country, email);
        System.out.println(booking.toString());

    }
}

