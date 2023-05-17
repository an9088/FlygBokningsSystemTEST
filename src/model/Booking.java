package model;

import controller.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Booking {

    private int bookingNumber;
    private int nbrOfTravelers;

    private Controller controller;

    private ArrayList bookings = new ArrayList();
    private String name, lastName, address, zip, country, city, email, bookingDetails, airport, fullName;


    public Booking(String fullName, String address, String city, String zip, String country, String email,
                   int bookingNumber, String bookingDetails, String airport, Controller controller) {
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.email = email;
        this.bookingNumber = bookingNumber;
        this.controller = controller;
        this.bookingDetails = bookingDetails;
        this.airport = airport;

        bookingConfirmationForSignedInUser(fullName, address, city, zip, country, email, airport, bookingNumber, bookingDetails);
    }


    public Booking(String name, String lastName, String address, String city, String zip, String country,
                   String email, int bookingNumber, String bookingDetails, String airport, Controller controller) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.email = email;
        this.bookingNumber = bookingNumber;
        this.controller = controller;
        this.bookingDetails = bookingDetails;
        this.airport = airport;

        bookingConfirmation(name, lastName, address, city, zip, country, email, airport, bookingDetails, bookingNumber);
    }


    private void bookingConfirmation(String name, String lastName, String address, String city,
                                     String zip, String country, String email, String bookingDetails, String airport, int bookingNumber) {

        
        String bookingMessage = "Your booking number: " + bookingNumber + "\n\n" +
                "Thank you " + name + " " + lastName + " for using this application for booking your flight tickets. \n" +
                "We hope you will have a pleasant stay in " + bookingDetails + "\n" +
                "Here are your booking details for your upcoming trip:\n\n" + airport  +
                "\nFull name: " + name + " " + lastName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n";


        controller.showBookingConfirmation(bookingMessage);
        saveBookingToFile(bookingMessage);


    }

    private void bookingConfirmationForSignedInUser(String fullName, String address, String city, String zip,
                                                    String country, String email, String airport, int bookingNumber, String bookingDetails) {

        String bookingMessage = "Your booking number: " + bookingNumber + "\n\n" +
                "Thank you " + fullName + " for using this application for booking your flight tickets. \n" +
                "We hope you will have a pleasant stay in " +  bookingDetails + "\n" +
                "Here are your booking details for your upcoming trip:\n\n" + airport +
                "\nFull name: " + fullName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n";


        controller.showBookingConfirmation(bookingMessage);
        saveBookingToFile(bookingMessage);
    }


    private void saveBookingToFile(String bookingMessage){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("booking.txt",true));
            writer.write(bookingMessage);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save booking to file" + e.getMessage());
        }
    }



}
