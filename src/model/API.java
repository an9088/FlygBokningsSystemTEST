package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import controller.Controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class API {

    private String departureAirport;

    private String destinationAirport;

    private String date;

    private Controller controller;

    private ArrayList<String> flightInfo = new ArrayList<String>();

    public API(String depAirport, String arrAirport, String date, Controller controller) throws IOException, ParserConfigurationException, InterruptedException, SAXException {

        this.departureAirport = depAirport;
        this.destinationAirport = arrAirport;
        this.date = date;
        this.controller = controller;

        searchFlights(depAirport, arrAirport, date);
    }

    private void searchFlights(String depAirport, String arrAirport, String date) throws IOException, InterruptedException, ParserConfigurationException, SAXException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://timetable-lookup.p.rapidapi.com/TimeTable/" + depAirport + "/" + arrAirport + "/" + date + "/"))
                .header("X-RapidAPI-Key", "ce88d20303msh70939666ef817abp16916cjsn12852aec3cfd")
                .header("X-RapidAPI-Host", "timetable-lookup.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(response.body())));
        document.getDocumentElement().normalize();

        NodeList flightLegDetailsList = document.getElementsByTagName("FlightLegDetails");

        // rubrikrad
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                "Departure Time", "Departure Offset", "Arrival Time", "Arrival Offset",
                "Flight Number", "Duration");


        for (int i = 0; i < flightLegDetailsList.getLength(); i++) {
            Node flightLegDetailsNode = flightLegDetailsList.item(i);
            if (flightLegDetailsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element flightLegDetailsElement = (Element) flightLegDetailsNode;
                String departureTime = flightLegDetailsElement.getAttribute("DepartureDateTime").substring(0, 16);
                String departureOffset = flightLegDetailsElement.getAttribute("FLSDepartureTimeOffset");
                String arrivalTime = flightLegDetailsElement.getAttribute("ArrivalDateTime").substring(0, 16);
                String arrivalOffset = flightLegDetailsElement.getAttribute("FLSArrivalTimeOffset");
                String flightNumber = flightLegDetailsElement.getAttribute("FlightNumber");
                String duration = flightLegDetailsElement.getAttribute("JourneyDuration");
                String airline = flightLegDetailsElement.getAttribute("Airline");

                // Datarad
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                        departureTime, departureOffset, arrivalTime, arrivalOffset, flightNumber, duration);
                String message = "==================================" +
                        "\nDeparture time: " + departureTime + "\nDPT offset: " + departureOffset +
                        "\nArrival time: " + arrivalTime + "\nARR offset: " + arrivalOffset +
                        "\nFlight number: " + flightNumber + "\nDuration: " + duration + "\n";

                flightInfo.add(message);
                controller.setDisplayMessage(flightInfo);

            }
        }
    }


}
