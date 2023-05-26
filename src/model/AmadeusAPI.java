package model;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.City;
import com.amadeus.resources.FlightOfferSearch;
import controller.Controller;

import java.util.ArrayList;

/**
 * This Class handle all the requests to the API. There are several requests made in different methods
 * to make sure the search for available flights can be done with ease for the users of the application.
 * @author Mattias Malm
 */

public class AmadeusAPI {

    private Controller controller;
    private ArrayList<String> flightInfo = new ArrayList<String>();

    private ArrayList<String> flightDisplay = new ArrayList<String>();

    private String departureCity, destinationCity, date, returnDate, destinationCode, upperCaseAirport, upperCaseDestAirport, departureCode;

    private int nbrOfPassengers;

    private FlightOfferSearch[] flightOffersSearches;

    private Amadeus amadeusFlightSearch;

    /**
     * This constructor is called when the user wants to search for return tickets. Here, all the variables are initialized
     * and passed on to the searchReturnTickets-method were they are further used for all the requests.
     * @param departureCity - the given departure city which will be converted to the correct IATA code further down.
     * @param destinationCity - the given destination city which will be converted to the correct IATA code further down.
     * @param date - the flyout date
     * @param returnDate - the return date
     * @param nbrOfPassengers - the total amount of passengers
     * @param controller - an instance of the controller class which is used to pass on information to the mainframe class
     */

    public AmadeusAPI(String departureCity, String destinationCity, String date, String returnDate, int nbrOfPassengers, Controller controller) {
        this.controller = controller;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.date = date;
        this.returnDate = returnDate;
        this.nbrOfPassengers = nbrOfPassengers;
        connectToApi();
        searchReturnTickets(departureCity, destinationCity, nbrOfPassengers, date, returnDate);

    }

    /**
     * This constructor is called when the user wants to search for one way tickets. Here, all the variables are initialized
     * and passed on to the searchOneWayTicket-method were they are further used for all the requests.
     * @param departureCity - the given departure city which will be converted to the correct IATA code further down.
     * @param destinationCity - the given destination city which will be converted to the correct IATA code further down.
     * @param date - the flyout date
     * @param nbrOfPassengers - the total amount of passengers
     * @param controller - an instance of the controller class which is used to pass on information to the mainframe class
     */
    public AmadeusAPI(String departureCity, String destinationCity, String date, int nbrOfPassengers, Controller controller) {
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.nbrOfPassengers = nbrOfPassengers;
        this.date = date;
        this.controller = controller;
        connectToApi();
        searchOneWayTicket(departureCity, destinationCity, nbrOfPassengers, date);
    }

    /**
     * In this method, the connection to the API is established.
     */
    private void connectToApi() {
        amadeusFlightSearch = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();
    }

    /**
     * In this method, the passed on variables from the constructor are used to create the request for the API.
     * First, each destination are being passed on to the formatCityName to make sure the departure and arrival
     * destinations are always well formatted (mAlMö --> Malmö). This makes it easier to transform the city names to
     * the correct IATA code which is essential to make the actual request. That is what happens in the getIataCode-method.
     * With the correct IATA codes available, the flight requests can be carried out.
     *
     * When the request is made, the api returns the available information about each specific flight.
     * If flights were found, the information is passed on to the displayMessage-method.
     * @param departureCity - the given departure city which will be converted to the correct IATA code further down.
     * @param destinationCity - the given destination city which will be converted to the correct IATA code further down.
     * @param nbrOfPassengers - the total amount of passengers
     * @param date - the flyout date
     * @param returnDate - the return date
     */
    public void searchReturnTickets(String departureCity, String destinationCity, int nbrOfPassengers
            , String date, String returnDate) {

        flightInfo.clear();

        departureCity = formatCityName(departureCity);
        System.out.println(departureCity);
        destinationCity = formatCityName(destinationCity);

        departureCode = getIataCode(departureCity);
        destinationCode = getIataCode(destinationCity);

        if (departureCode.equals(destinationCode)) {
            controller.errorCode("Please enter two different destinations");
        } else {

            //This is where the flight offers are compared - this is where the request happens
            try {
                flightOffersSearches = amadeusFlightSearch.shopping.flightOffersSearch.get(
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
                String message = "No trips were found. Please try again.";
                controller.errorCode(message);
                return;
               // throw new RuntimeException(e);
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
                        departureCity, destinationCity);

            }
        }

    }

    /**
     * This method creates two Strings, one for the list of the available flights, another for a more detailed information
     * screen of each individual flight, selected by the user.
     * @param stopovers - number of stops during the departure trip.
     * @param returnStopovers - number of stops during the return trip.
     * @param departureIATA - IATA code of the departure airport.
     * @param destinationIATA - IATA code of the destination airport.
     * @param departureTime - departure time of the first flight.
     * @param airline - airline carrier code.
     * @param returnDepartureIATA - IATA code of the departure airport of the return trip.
     * @param returnDestinationIATA - IATA code of the destination airport of the return trip.
     * @param returnDepartureTime - departure time of the return flight.
     * @param returnAirline - airline carrier code.
     * @param finalPrice - the total cost of the trip.
     * @param departureCity - departure city.
     * @param destinationCity - destination city.
     */
    private void displayMessage(int stopovers, int returnStopovers, String departureIATA, String destinationIATA, String departureTime, String airline,
                                String returnDepartureIATA, String returnDestinationIATA, String returnDepartureTime,
                                String returnAirline, double finalPrice, String departureCity, String destinationCity) {

        String flights =
                "\nFrom: " + departureIATA +
                        "\n To: " + destinationIATA +
                        "\n <-->" +
                        "\n Return from: " + returnDepartureIATA +
                        "\n To: " + returnDestinationIATA +
                        "\n\n| Total price: " + finalPrice + "€\n";

        String flightDisplayInfo =
                "\nDeparture date: " + departureTime +
                        "\nFrom: " + departureCity + " " + departureIATA +
                        "\nTo: " + destinationCity + " " + destinationIATA +
                        "\nAirline: " + airline;
        if (stopovers > 0) {
            flightDisplayInfo += "\nNumber of stops: " + stopovers;
        }

        flightDisplayInfo += "\n<----->" +
                "\nDeparture date: " + returnDepartureTime +
                "\nReturn from: " + destinationCity + " " + returnDepartureIATA +
                "\nReturn to: " + departureCity + " " + returnDestinationIATA +
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


    /**
     * This method does the exact same thing as the searchReturnTickets, except it only searches for one way tickets instead.
     * The difference here is that there is no request made for any return flights.
     * @param departureCity - the given departure city which will be converted to the correct IATA code further down.
     * @param destinationCity - the given destination city which will be converted to the correct IATA code further down.
     * @param nbrOfPassengers - the total amount of passengers.
     * @param date - the flyout date.
     */
    public void searchOneWayTicket(String departureCity, String destinationCity, int nbrOfPassengers, String date) {

        flightInfo.clear();
        flightDisplay.clear();

        departureCity = formatCityName(departureCity);
        destinationCity = formatCityName(destinationCity);


        departureCode = getIataCode(departureCity);
        destinationCode = getIataCode(destinationCity);


        if (departureCode.equals(destinationCode)) {
            controller.errorCode("Please enter two different destinations");
        } else {

            FlightOfferSearch[] flightOffersSearches = new FlightOfferSearch[0];// Sortera efter pris
            try {
                flightOffersSearches = amadeusFlightSearch.shopping.flightOffersSearch.get(
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
                        destinationCity, departureCity);


            }
        }
    }

    /**
     * This method is used when a warning message occurs on the screen. It makes sure that there are no visible trips
     * for the user to se if an error occurs. Basically it resets the two main fields where all the flight information
     * is shown.
     */

    private void displayNothing() {
        String nothing = "";
        flightInfo.add(nothing);
        flightDisplay.add(nothing);
        controller.setDisplayMessage(flightInfo, flightDisplay);
    }

    /**
     * This method has the same functionality as the displayMessage-method.
     * @param stopovers - number of stops.
     * @param departureIATA - IATA code of the departure airport/city.
     * @param destinationIATA - IATA code of the destination airport/city.
     * @param departureTime - the departure time.
     * @param airline - airline carrier code.
     * @param price - the total price of the trip.
     * @param destinationCity - the destination city.
     * @param departureCity - the departure city.
     */

    private void displayOneWayTicket(int stopovers, String departureIATA, String destinationIATA, String departureTime,
                                     String airline, String price, String destinationCity, String departureCity) {

        // Adding flight information to flightInfo ArrayList
        String flights1 = "\nFrom: " + departureIATA +
                "\n To: " + destinationIATA +
                "\n Airline: " + airline +
                "\n\n| Total price: " + price + "€\n";

        String flightDisplayInfo1 =
                "\nDeparture time: " + departureTime +
                        "\nFrom: " + departureCity + " " + departureIATA +
                        "\nTo: " + destinationCity + " " + destinationIATA +
                        "\nAirline: " + airline;
        if (stopovers > 0) {
            flightDisplayInfo1 += "\nNumber of stops: " + stopovers;
        }
        flightDisplayInfo1 += "\n\nTotal price: " + price + "€\n";


        flightDisplay.add(flightDisplayInfo1);
        flightInfo.add(flights1);
        controller.setDisplayMessage(flightInfo, flightDisplay);

    }

    /**
     * This method is used as a check for the most common swedish airports. Since the requests must be made in english,
     * it won't be possible to search for a swedish city. However, it is very likely that a new user will use swedish
     * spelling when searching for flights. Therefore, this method converts the cities to their english equivalents.
     * @param city - the city to be converted to english.
     * @return - the english translated city to be used for the search flight.
     */
    private String checkCitySpelling(String city) {

        if (city.equals("Malmö")) {
            city = "Malmo";
        }
        if (city.equals("Luleå")) {
            city = "Lulea";
        }
        if (city.equals("Ängelholm")) {
            city = "Angelholm";
        }
        if (city.equals("Köpenhamn")) {
            city = "Copenhagen";
        }
        if (city.equals("Östersund")) {
            city = "Oestersund";
        }
        if (city.equals("Örebro")) {
            city = "Oerebro";
        }
        if(city.equals("Göteborg")) {
            city = "Goeteborg";
        }
        if(city.equals("Växjö")) {
            city = "Vaexjoe";
        }
        return city;
    }

    /**
     * This method is used to convert the cities to their IATA codes. This method is essential for the flight requests to
     * be made. Otherwise, each user would have to know, or simply guess the IATA code for any given city.
     * @param city - the city to be converted to its IATA code.
     * @return - the correct IATA code.
     */
    private String getIataCode(String city) {
        String iataCode = null;
        City[] cities = null;

        try {
            cities = amadeusFlightSearch.referenceData.locations.cities.get(Params.with("keyword", city));
        } catch (ResponseException e) {
            System.out.println("ERROR");
            throw new RuntimeException(e);
        } catch (Exception e) {
            displayNothing();
            controller.errorCode("Error: Location is not valid");
        }

        if (cities != null && cities.length > 0) {
            iataCode = cities[0].getIataCode();
            System.out.println("IATA-kod för " + city + " är " + iataCode);
        }

        return iataCode;
    }

    /**
     * This method makes sure that all the cities are correctly formatted. It's mostly an aesthetic enhancer as the
     * upperCaseDestAirport is later used for the booking confirmation. In the booking confirmation, each user should se
     * their destinations as Malmö to Stockholm, and not as mAlmO to sTOCKholm.
     * @param correctCity - the city the is reformatted in this method.
     * @return - the correctly formatted city.
     */

    private String formatCityName(String correctCity) {
        String str1 = correctCity;
        upperCaseDestAirport = str1.substring(0, 1).toUpperCase() + str1.substring(1).toLowerCase();
        System.out.println(upperCaseDestAirport);

        if (upperCaseDestAirport.contains("å") || upperCaseDestAirport.contains("ä") || upperCaseDestAirport.contains("ö")
                || upperCaseDestAirport.contains("Å") || upperCaseDestAirport.contains("Ä") || upperCaseDestAirport.contains("Ö")) {
            upperCaseDestAirport = checkCitySpelling(upperCaseDestAirport);
        }
        return upperCaseDestAirport;
    }

    public String getDestinationCity() {
        return formatCityName(destinationCity);
    }


}
