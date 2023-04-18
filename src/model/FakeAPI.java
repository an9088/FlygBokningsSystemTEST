package model;

import controller.Controller;

import java.util.ArrayList;

public class FakeAPI {

    /**
     * @author Ellyas Rahimy
     * @co-author Mattias Malm
     */

    private String departureAirport;

    private String destinationAirport;

    private String date;

    private Controller controller;

    private ArrayList<String> flightInfo = new ArrayList<String>();

    public FakeAPI(String departureAirport, String destinationAirport, String date, Controller controller) {

        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.date = date;
        this.controller = controller;

        String message = "==================================" +
                "\nFrom: " + departureAirport +
                "\nTo: " + destinationAirport +
                "\nDeparture date: " + date +
                "\nDeparture time: 07:00AM "  + "\nDPT offset: +2H"  +
                "\nArrival time: 08:30AM"  + "\nARR offset: 30M" +
                "\nFlight number: RYN123AN" + "\nDuration: 1H:30M"  + "\n";

        String message1 = "==================================" +
                "\nFrom: " + departureAirport +
                "\nTo: " + destinationAirport +
                "\nDeparture date: " + date +
                "\nDeparture time: 08:00AM "  + "\nDPT offset: +2H"  +
                "\nArrival time: 09:30AM"  + "\nARR offset: 30M" +
                "\nFlight number: RYN122AN" + "\nDuration: 1H:30M"  + "\n";

        String message2 = "==================================" +
                "\nFrom: " + departureAirport +
                "\nTo: " + destinationAirport +
                "\nDeparture date: " + date +
                "\nDeparture time: 10:00PM "  + "\nDPT offset: +2H"  +
                "\nArrival time: 11:30PM"  + "\nARR offset: 30M" +
                "\nFlight number: RYN121AN" + "\nDuration: 1H:30M"  + "\n";




        flightInfo.add(message);
        flightInfo.add(message1);
        flightInfo.add(message2);
       // controller.setDisplayMessage(flightInfo);
    }
}
