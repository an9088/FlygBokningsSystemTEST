package src.controller;

import src.model.Airline;

public class Main {
    public static void main(String[] args) {


        Controller controller = new Controller();


        Airline airline = new Airline("WizzAir", "W123AN1324");
        System.out.println("Added a new airline");
        System.out.println(airline.toString());

        System.out.println();

       // Passenger passenger = new Passenger("Mattias", "Malm", "xxxxxx-xxxx", "Magnoliavägen 3" );
       // System.out.println(passenger.toString());
    }
}
