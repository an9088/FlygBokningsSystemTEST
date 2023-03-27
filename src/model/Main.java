package src.model;

import src.controller.Controller;
import src.view.Mainframe;

public class Main {
    public static void main(String[] args) {


        //Mainframe frame = new Mainframe();
        Controller controller = new Controller();

        Airline airline = new Airline("WizzAir", "W123AN1324");
        System.out.println("Added a new airline");
        System.out.println(airline.toString());

        System.out.println();

        Passenger passenger = new Passenger("Mattias", "Malm", "19971215-6513", "Magnoliavägen 3" );
        System.out.println(passenger.toString());
    }
}
