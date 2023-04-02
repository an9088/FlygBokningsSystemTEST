package src.main;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import src.view.Mainframe;

public class api_test {
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
		System.out.println("Welcome to the Flight Booking System!");

		String departureAirport = getDepartureAirport();
		String destinationAirport = getDestinationAirport();
		String date = getDate();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://timetable-lookup.p.rapidapi.com/TimeTable/" + departureAirport + "/" + destinationAirport + "/" + date + "/"))
				.header("X-RapidAPI-Key", "5bdf7d1e5amshd77e7d6fbee5d6cp1cdf9ejsna5b67528fced")
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

		        // Datarad
		        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
		            departureTime, departureOffset, arrivalTime, arrivalOffset, flightNumber, duration);




		    }
		}









		System.out.println("Thank you for using the Flight Booking System!");

	}

	private static String getDepartureAirport() {
		System.out.println("Please enter departure Airport: ");
		return scanner.nextLine();
	}

	private static String getDestinationAirport() {
		System.out.println("Please enter destination Airport: ");
		return scanner.nextLine();
	}

	private static String getDate() {
		System.out.println("Please enter date (format yyyymmdd): ");
		return scanner.nextLine();
	}

}
