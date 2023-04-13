package model;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referencedata.Locations;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.Location;
import controller.Controller;

import java.util.ArrayList;

public class AmadeusAPI {

    private Controller controller;
    private ArrayList<String> flightInfo = new ArrayList<String>();

    private String departureAirport, destinationAirport, date;

    private int nbrOfPassengers;

    public AmadeusAPI(String departureAirport, String destinationAirport, String date, int nbrOfPassengers, Controller controller) throws ResponseException {
        this.controller = controller;
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.date = date;
        this.nbrOfPassengers = nbrOfPassengers;
        tryApi(departureAirport, destinationAirport,nbrOfPassengers, date);
    }


    public void tryApi(String departureAirport, String destinationAirport, int nbrOfPassengers, String date) throws ResponseException {

        Amadeus amadeus = Amadeus
                .builder("JZg5Dd12jmmomi3ZDX2lrq1KQNBUPtQx", "GsQLYokKGb6h4cxc")
                .build();

        Location[] locations = amadeus.referenceData.locations.get(Params
                .with("keyword", "LON")
                .and("subType", Locations.ANY));

        FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", departureAirport)
                        .and("destinationLocationCode", destinationAirport)
                        .and("departureDate", date)
                        .and("returnDate", date) // date står här endast i testningssyfte 2023-04-13 Mattias Malm
                        .and("adults", nbrOfPassengers)
                        .and("nonStop", true)
                        .and("max", 10));

        for (int i = 0; i < flightOffersSearches.length; i++) {
            String departure = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getIataCode();
            String destination = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getArrival().getIataCode();
            String departureTime = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getDeparture().getAt();
            String airline = flightOffersSearches[i].getItineraries()[0].getSegments()[0].getCarrierCode();
            String price = flightOffersSearches[i].getPrice().getTotal();


                // Adding flight information to flightInfo ArrayList
            String flights =
                    "==================================" +
                    "\nFrom: " + departure +
                    "\nTo: " + destination +
                    "\nDeparture date: " + departureTime +
                    "\nAirline: " + airline +
                    "\nPrice: " + price +
                    "\n";
            flightInfo.add(flights);
            controller.setDisplayMessage(flightInfo);
        }


        //String locationsResponseBody = locations[0].getResponse().getBody();
        //System.out.println("Locations API response body: " + locationsResponseBody);


    }
}
