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

    private String departureAirport, destinationAirport, date, returnDate, upperCaseAirport, upperCaseDestAirport;

    private int nbrOfPassengers;

    private FlightOfferSearch[] flightOffersSearches;


    private Amadeus amadeus;


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

    public AmadeusAPI() {

    }

    public void searchReturnTickets(String departureAirport, String destinationAirport, int nbrOfPassengers
            , String date, String returnDate) throws ResponseException {

        flightInfo.clear();


        //These two small blocks ensures that the departure and destination city always are well formatted.
        String str = departureAirport;
        upperCaseAirport = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        System.out.println(upperCaseAirport);

        String str1 = destinationAirport;
        upperCaseDestAirport = str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
        System.out.println(upperCaseDestAirport);

        //Here, connection is established to the API
        amadeus = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();

        //This is where searched cities are converted to IATA-codes ex: Malmo -> MMA
        City[] cities = amadeus.referenceData.locations
                .cities.get(Params.with("keyword", departureAirport));

        String departureCode = "";
        String destinationCode = "";

        if (cities.length > 0) {
            departureCode = cities[0].getIataCode();
            System.out.println("IATA-kod för " + departureAirport + " är " + departureCode);
        }

        City[] cities1 = amadeus.referenceData.locations
                .cities.get(Params.with("keyword", destinationAirport));

        if (cities.length > 0) {
            destinationCode = cities1[0].getIataCode();
            System.out.println("IATA-kod för " + destinationAirport + " är " + destinationCode);
        }

        if (departureCode.equals(destinationCode)) {
            controller.errorCode("Please enter two different destinations");
        } else {

            //This is where the flight offers are compared - this is where the request happens
            flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                    Params.with("originLocationCode", departureCode)
                            .and("destinationLocationCode", destinationCode)
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

                String flights =
                        "\nFrom: " + departure +
                                "\nTo: " + destination +
                                "\n<-->" +
                                "\nReturn from: " + returnDeparture +
                                "\nReturn to: " + returnDestination +
                                "\n\nTotal price: " + finalPrice + "€\n";

                String flightDisplayInfo =
                        "\nDeparture date: " + departureTime +
                                "\nFrom: " + upperCaseAirport + " " + departure +
                                "\nTo: " + upperCaseDestAirport + " " + destination +
                                "\nAirline: " + airline +
                                "\n<----->" +
                                "\nDeparture date: " + returnDepartureTime +
                                "\nReturn from: " + upperCaseDestAirport + " " + returnDeparture +
                                "\nReturn to: " + upperCaseAirport + " " + returnDestination +
                                "\nAirline: " + returnAirline +
                                "\n\nTotal price: " + finalPrice + "€\n";

                System.out.println(finalPrice + "€");
                flightDisplay.add(flightDisplayInfo);
                flightInfo.add(flights);
                controller.setDisplayMessage(flightInfo, flightDisplay);

            }
        }

    }

    public void searchOneWayTicket(String departureAirport, String destinationAirport, int nbrOfPassengers, String date) throws ResponseException {


        flightInfo.clear();

        String str = departureAirport;
        upperCaseAirport = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        System.out.println(upperCaseAirport);

        String str1 = destinationAirport;
        upperCaseDestAirport = str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
        System.out.println(upperCaseDestAirport);


        Amadeus amadeus = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();

        City[] cities = amadeus.referenceData.locations
                .cities.get(Params.with("keyword", departureAirport));

        String departureCode = "";
        String destinationCode = "";
        if (cities.length > 0) {
            departureCode = cities[0].getIataCode();
            System.out.println("IATA-kod för " + departureAirport + " är " + departureCode);
        }

        City[] cities1 = amadeus.referenceData.locations
                .cities.get(Params.with("keyword", destinationAirport));

        if (cities.length > 0) {
            destinationCode = cities1[0].getIataCode();
            System.out.println("IATA-kod för " + destinationAirport + " är " + destinationCode);
        }
        if (departureCode.equals(destinationCode)) {
            controller.errorCode("Please enter two different destinations");
        } else {

            FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                    Params.with("originLocationCode", departureCode)
                            .and("destinationLocationCode", destinationCode)
                            .and("departureDate", date)
                            .and("returnDate", date)
                            .and("adults", nbrOfPassengers)
                            .and("nonStop", true)
                            .and("max", 10));// Sortera efter pris


            for (int i = 0; i < flightOffersSearches.length; i++) {
                String departure = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getIataCode();
                String destination = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getArrival().getIataCode();
                String departureTime = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getAt();
                String airline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
                String price = flightOffersSearches[i].getPrice().getTotal();

                // Adding flight information to flightInfo ArrayList
                String flights1 = "\nFrom: " + departure +
                        "\nTo: " + destination +
                        "\nAirline: " + airline +
                        "\n\nTotal price: " + price + "€\n";

                String flightDisplayInfo1 =
                        "\nDeparture time: " + departureTime +
                                "\nFrom: " + upperCaseAirport + " " + departure +
                                "\nTo: " + upperCaseDestAirport + " " + destination +
                                "\nAirline: " + airline +
                                "\n\nTotal price: " + price + "€\n";
                ;

                flightDisplay.add(flightDisplayInfo1);
                flightInfo.add(flights1);
                controller.setDisplayMessage(flightInfo, flightDisplay);


            }
        }


    }

    public String getUpperCaseDestAirport() {
        return upperCaseDestAirport;
    }
}
