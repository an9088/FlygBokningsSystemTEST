package model;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.City;
import com.amadeus.resources.FlightOfferSearch;
import controller.Controller;

import java.util.ArrayList;

public class AmadeusAPI {

    private Controller controller;
    private ArrayList<String> flightInfo = new ArrayList<String>();

    private ArrayList<String> flightDisplay = new ArrayList<String>();

    private String departureAirport, destinationAirport, date, returnDate, destinationCode, upperCaseAirport, upperCaseDestAirport, departureCode;

    private int nbrOfPassengers;

    private FlightOfferSearch[] flightOffersSearches;


    private Amadeus amadeus;


    public AmadeusAPI(String departureAirport, String destinationAirport, String date, String returnDate, int nbrOfPassengers, Controller controller) {
        this.controller = controller;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.date = date;
        this.returnDate = returnDate;
        this.nbrOfPassengers = nbrOfPassengers;
        connectToApi();
        searchReturnTickets(departureAirport, destinationAirport, nbrOfPassengers, date, returnDate);

    }

    public AmadeusAPI(String departureAirport, String destinationAirport, String date, int nbrOfPassengers, Controller controller) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.nbrOfPassengers = nbrOfPassengers;
        this.date = date;
        this.controller = controller;
        connectToApi();
        searchOneWayTicket(departureAirport, destinationAirport, nbrOfPassengers, date);
    }

    public AmadeusAPI() {
    }

    private void connectToApi() {
        amadeus = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();
    }


    public void searchReturnTickets(String departureAirport, String destinationAirport, int nbrOfPassengers
            , String date, String returnDate) {

        flightInfo.clear();

        departureAirport = formatAirportName(departureAirport);
        destinationAirport = formatAirportName(destinationAirport);

        departureCode = getIataCode(departureAirport);
        destinationCode = getIataCode(destinationAirport);

        if (departureCode.equals(destinationCode)) {
            controller.errorCode("Please enter two different destinations");
        } else {

            //This is where the flight offers are compared - this is where the request happens
            try {
                flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                        Params.with("originLocationCode", departureCode)
                                .and("destinationLocationCode", destinationCode)
                                .and("departureDate", date)
                                .and("returnDate", returnDate)
                                .and("adults", nbrOfPassengers)
                                //.and("nonStop", true)
                                .and("max", 10));// Sortera efter pris

                if (flightOffersSearches.length == 0) {
                    displayNothing();
                    controller.errorCode("No available trips were found. Please try again with different dates.");
                }

            } catch (ResponseException e) {
                throw new RuntimeException(e);
            }


            for (int i = 0; i < flightOffersSearches.length; i++) {

                FlightOfferSearch.Itinerary[] itineraries = flightOffersSearches[i].getItineraries();

                int stopovers = itineraries[0].getSegments().length - 1;
                int returnStopovers = itineraries[1].getSegments().length - 1;

                String departure = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getIataCode();
                String destination = flightOffersSearches[i].getItineraries()[0]
                        .getSegments()[flightOffersSearches[i].getItineraries()[0]
                        .getSegments().length - 1].getArrival().getIataCode();
                String departureTime = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getAt();
                String airline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
                String price = flightOffersSearches[i].getPrice().getTotal();

                String returnDeparture = flightOffersSearches[i].getItineraries()[1].getSegments()[0].getDeparture().getIataCode(); // Lägger till returflygets avgångsinformation
                String returnDestination = flightOffersSearches[i].getItineraries()[1]
                        .getSegments()[flightOffersSearches[i].getItineraries()[1]
                        .getSegments().length - 1].getArrival().getIataCode(); // Lägger till returflygets destinationsinformation
                String returnDepartureTime = flightOffersSearches[i].getItineraries()[1].getSegments()[0].getDeparture().getAt();
                String returnAirline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
                String returnPrice = flightOffersSearches[i].getPrice().getTotal();


                double finalPrice = Double.parseDouble(price) + Double.parseDouble(returnPrice);

                displayMessage(stopovers, returnStopovers, departure, destination, departureTime,
                        airline, returnDeparture, returnDestination, returnDepartureTime, returnAirline, finalPrice,
                        departureAirport, destinationAirport);

            }
        }

    }

    private void displayMessage(int stopovers, int returnStopovers, String departure, String destination, String departureTime, String airline,
                                String returnDeparture, String returnDestination, String returnDepartureTime,
                                String returnAirline, double finalPrice, String departureAirport, String destinationAirport) {

        String flights =
                "\nFrom: " + departure +
                        "\n To: " + destination +
                        "\n <-->" +
                        "\n Return from: " + returnDeparture +
                        "\n To: " + returnDestination +
                        "\n\n| Total price: " + finalPrice + "€\n";

        String flightDisplayInfo =
                "\nDeparture date: " + departureTime +
                        "\nFrom: " + departureAirport + " " + departure +
                        "\nTo: " + destinationAirport + " " + destination +
                        "\nAirline: " + airline;
        if (stopovers > 0) {
            flightDisplayInfo += "\nNumber of stops: " + stopovers;
        }

        flightDisplayInfo += "\n<----->" +
                "\nDeparture date: " + returnDepartureTime +
                "\nReturn from: " + destinationAirport + " " + returnDeparture +
                "\nReturn to: " + departureAirport + " " + returnDestination +
                "\nAirline: " + returnAirline;
        if (stopovers > 0) {
            flightDisplayInfo += "\nNumber of stops: " + returnStopovers;
        }
        flightDisplayInfo += "\n\nTotal price: " + finalPrice + "€\n";

        System.out.println(finalPrice + "€");
        flightDisplay.add(flightDisplayInfo);
        flightInfo.add(flights);
        controller.setDisplayMessage(flightInfo, flightDisplay);
    }


    public void searchOneWayTicket(String departureAirport, String destinationAirport, int nbrOfPassengers, String date) {

        flightInfo.clear();
        flightDisplay.clear();

        departureAirport = formatAirportName(departureAirport);
        destinationAirport = formatAirportName(destinationAirport);


        departureCode = getIataCode(departureAirport);
        destinationCode = getIataCode(destinationAirport);


        if (departureCode.equals(destinationCode)) {
            controller.errorCode("Please enter two different destinations");
        } else {

            FlightOfferSearch[] flightOffersSearches = new FlightOfferSearch[0];// Sortera efter pris
            try {
                flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                        Params.with("originLocationCode", departureCode)
                                .and("destinationLocationCode", destinationCode)
                                .and("departureDate", date)
                                .and("returnDate", date)
                                .and("adults", nbrOfPassengers)
                                //.and("nonStop", true)
                                .and("max", 10));

                if (flightOffersSearches.length == 0) {
                    displayNothing();
                    controller.errorCode("No available trips were found. Please try again with different dates.");
                }
            } catch (ResponseException e) {
                throw new RuntimeException(e);
            }


            for (int i = 0; i < flightOffersSearches.length; i++) {

                FlightOfferSearch.Itinerary[] itineraries = flightOffersSearches[i].getItineraries();


                int stopovers = itineraries[0].getSegments().length - 1;
                //  int returnStopovers = itineraries[1].getSegments().length - 1;

                String departure = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getIataCode();
                String destination = flightOffersSearches[i].getItineraries()[0]
                        .getSegments()[flightOffersSearches[i].getItineraries()[0]
                        .getSegments().length - 1].getArrival().getIataCode();
                String departureTime = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getAt();
                String airline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
                String price = flightOffersSearches[i].getPrice().getTotal();


                displayOneWayTicket(stopovers, departure, destination, departureTime, airline, price,
                        destinationAirport, departureAirport);


            }
        }
    }

    private void displayNothing() {
        String nothing = "";
        flightInfo.add(nothing);
        flightDisplay.add(nothing);
        controller.setDisplayMessage(flightInfo, flightDisplay);
    }


    private void displayOneWayTicket(int stopovers, String departure, String destination, String departureTime,
                                     String airline, String price, String destinationAirport, String departureAirport) {

        // Adding flight information to flightInfo ArrayList
        String flights1 = "\nFrom: " + departure +
                "\n To: " + destination +
                "\n Airline: " + airline +
                "\n\n| Total price: " + price + "€\n";

        String flightDisplayInfo1 =
                "\nDeparture time: " + departureTime +
                        "\nFrom: " + departureAirport + " " + departure +
                        "\nTo: " + destinationAirport + " " + destination +
                        "\nAirline: " + airline;
        if (stopovers > 0) {
            flightDisplayInfo1 += "\nNumber of stops: " + stopovers;
        }
        flightDisplayInfo1 += "\n\nTotal price: " + price + "€\n";


        flightDisplay.add(flightDisplayInfo1);
        flightInfo.add(flights1);
        controller.setDisplayMessage(flightInfo, flightDisplay);

    }

    private String checkCitySpelling(String airport) {

        if (airport.equals("Malmö")) {
            airport = "Malmo";
        }
        if (airport.equals("Luleå")) {
            airport = "Lulea";
        }
        if (airport.equals("Ängelholm")) {
            airport = "Angelholm";
        }
        if (airport.equals("Köpenhamn")) {
            airport = "Copenhagen";
        }
        if (airport.equals("Östersund")) {
            airport = "Oestersund";
        }
        if (airport.equals("Örebro")) {
            airport = "Oerebro";
        }
        if(airport.equals("Göteborg")) {
            airport = "Goeteborg";
        }
        if(airport.equals("Växjö")) {
            airport = "Vaexjoe";
        }
        return airport;
    }

    private String getIataCode(String airport) {

        String iataCode = null;
        //This is where searched cities are converted to IATA-codes ex: Malmo -> MMA
        City[] cities = new City[0];
        try {
            cities = amadeus.referenceData.locations
                    .cities.get(Params.with("keyword", airport));


        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }

        if (cities.length > 0) {
            iataCode = cities[0].getIataCode();
            System.out.println("IATA-kod för " + airport + " är " + iataCode);
        }
        return iataCode;
    }

    private String formatAirportName(String correctAirport) {
        String str1 = correctAirport;
        upperCaseDestAirport = str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
        System.out.println(upperCaseDestAirport);

        if (upperCaseDestAirport.contains("å") || upperCaseDestAirport.contains("ä") || upperCaseDestAirport.contains("ö")
                || upperCaseDestAirport.contains("Å") || upperCaseDestAirport.contains("Ä") || upperCaseDestAirport.contains("Ö")) {
            upperCaseDestAirport = checkCitySpelling(upperCaseDestAirport);
        }
        return upperCaseDestAirport;
    }

    public String getDestinationAirport() {
        return formatAirportName(destinationAirport);
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
}
