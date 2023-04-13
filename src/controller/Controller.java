package src.controller;

import com.amadeus.exceptions.ResponseException;
import org.xml.sax.SAXException;
import src.model.AirportCode;
import src.model.AmadeusExample;
import src.model.Booking;
import src.view.Mainframe;

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
        String year = mainframe.getYear().getValue().toString();
        String month = mainframe.getMonth().getValue().toString();
        String day = mainframe.getDay().getValue().toString();
        String date = year + "0" + month + "0" + day;

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

        /*if(month.startsWith("10") || month.startsWith("11") || month.startsWith("12"))
            date = year + month + "0" + day;
        if(!(day.startsWith("1,2,3,4,5,6,7,8,9")))
            date = year + "0"+ month + day;
        if(!(month.startsWith("1,2,3,4,5,6,7,8,9")) && (!(day.startsWith("1,2,3,4,5,6,7,8,9"))))
            date = year + month + day;

         */

            // API apiMessage = new API(departureAirport, destinationAirport, date, this);
            //FakeAPI fake = new FakeAPI(departureAirport, destinationAirport, date, this);
            AmadeusExample am = new AmadeusExample(departureAirport, destinationAirport, date, this);
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

