package src.controller;

import src.controller.Controller;
import src.model.Airline;
import src.model.Passenger;
import src.view.Mainframe;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        Controller controller = new Controller();

        Airline airline = new Airline("WizzAir", "W123AN1324");
        System.out.println("Added a new airline");
        System.out.println(airline.toString());

        System.out.println();

        Passenger passenger = new Passenger("Mattias", "Malm", "19971215-xxxx", "Magnoliavägen 3" );
        System.out.println(passenger.toString());
    }
}
