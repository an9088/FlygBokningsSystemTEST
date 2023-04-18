package model;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import controller.Controller;

import java.util.ArrayList;

public class AmadeusAPI {

    private Controller controller;
    private ArrayList<String> flightInfo = new ArrayList<String>();

    private String departureAirport, destinationAirport, date, returnDate;

    private int nbrOfPassengers;

    private FlightOfferSearch[] flightOffersSearches;

    private Amadeus amadeus;

    //String[] flightData = new String[10];

    public AmadeusAPI(String departureAirport, String destinationAirport, String date, String returnDate, int nbrOfPassengers, Controller controller) throws ResponseException {
        this.controller = controller;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.date = date;
        this.returnDate = returnDate;
        this.nbrOfPassengers = nbrOfPassengers;
        searchReturnTickets(departureAirport, destinationAirport, nbrOfPassengers, date, returnDate);
    }

    public AmadeusAPI(String departureAirport, String destinationAirport, String date, int nbrOfPassengers, Controller controller) throws ResponseException {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.nbrOfPassengers = nbrOfPassengers;
        this.date = date;
        this.controller = controller;
        searchOneWayTicket(departureAirport, destinationAirport, nbrOfPassengers, date);
    }


    public void searchReturnTickets(String departureAirport, String destinationAirport, int nbrOfPassengers
            , String date, String returnDate) throws ResponseException {

         amadeus = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();


        flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", departureAirport)
                        .and("destinationLocationCode", destinationAirport)
                        .and("departureDate", date)
                        .and("returnDate", returnDate)
                        .and("adults", nbrOfPassengers)
                        .and("nonStop", true)
                        .and("max", 10));// Sortera efter pris


        for (int i = 0; i < flightOffersSearches.length; i++) {
            String departure = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getIataCode();
            String destination = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getArrival().getIataCode();
            String departureTime = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getAt();
            String airline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
            String price = flightOffersSearches[i].getPrice().getTotal();

            String returnDeparture = flightOffersSearches[i].getItineraries()[1].getSegments()[0].getDeparture().getIataCode(); // Lägger till returflygets avgångsinformation
            String returnDestination = flightOffersSearches[i].getItineraries()[1].getSegments()[0].getArrival().getIataCode(); // Lägger till returflygets destinationsinformation
            String returnDepartureTime = flightOffersSearches[i].getItineraries()[1].getSegments()[0].getDeparture().getAt();
            String returnAirline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
            String returnPrice = flightOffersSearches[i].getPrice().getTotal();

            double finalPrice = Double.parseDouble(price) + Double.parseDouble(returnPrice);

            String flights = //"\n==================================" +
                    // "\nFirst flight" +
                    "\nFrom: " + departure +
                            "\nTo: " + destination +
                            // "\nDeparture date: " + departureTime +
                            // "\nAirline: " + airline +
                            "\n<-->" +
                            // "\nReturn flight" +
                            "\nReturn from: " + returnDeparture +
                            "\nReturn to: " + returnDestination +
                          //   "\nDeparture time: " + returnDepartureTime +
                            //  "\nAirline: " + returnAirline +
                            "\n\nTotal price: " + finalPrice + "€\n";

            System.out.println(finalPrice + "€");
            flightInfo.add(flights);
            controller.setDisplayMessage(flightInfo);


        }

    }

    public void searchOneWayTicket(String departureAirport, String destinationAirport, int nbrOfPassengers, String date) throws ResponseException {

        Amadeus amadeus = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();


        FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", departureAirport)
                        .and("destinationLocationCode", destinationAirport)
                        .and("departureDate", date)
                        .and("returnDate", date)
                        .and("adults", nbrOfPassengers)
                        .and("nonStop", true)
                        .and("max", 10));// Sortera efter pris


        for (int i = 0; i < flightOffersSearches.length; i++) {
            String departure = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getIataCode();
            String destination = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getArrival().getIataCode();
            String departureTime = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getAt();
            // String airline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
            String price = flightOffersSearches[i].getPrice().getTotal();

            // Adding flight information to flightInfo ArrayList
            String flights1 = "\nFrom: " + departure +
                    "\nTo: " + destination +
                    "\nDeparture time: " + departureTime +
                    "\n\nTotal price: " + price + "€\n";
            ;

            System.out.println("Hej");
            flightInfo.add(flights1);
            controller.setDisplayMessage(flightInfo);


        }
    }

}
