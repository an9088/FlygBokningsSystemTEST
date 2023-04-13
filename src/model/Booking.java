package model;

import controller.Controller;

import java.util.ArrayList;

public class Booking {

    private int bookingNumber;
    private int nbrOfTravelers;

    private Controller controller;

    private ArrayList bookings = new ArrayList();
    private String name;
    private String lastName;
    private String address;
    private String zip;
    private String country;
    private String city;
    private String email;

    private Booking newBooking;


    /*public Booking(String reservationNumber) {
        this.reservationNumber = reservationNumber;
        this.nbrOfTravelers = nbrOfTravelers;
    }

     */


    public Booking(String name, String lastName, String address, String city, String zip, String country, String email, int bookingNumber, Controller controller) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.email = email;
        this.bookingNumber = bookingNumber;
        this.controller = controller;

        bookingConfirmation(name, lastName, address, city, zip, country, email);
    }

    private void bookingConfirmation(String name, String lastName, String address, String city, String zip, String country, String email) {
        
        String bookingMessage = "Thank you " + name + " " + lastName + " for using this application for booking your flight tickets. \n" +
                "We hope you will have a pleasant stay in wherever the hell your traveling! \n\n\n" +
                "Your booking number: " + bookingNumber + "\n" +
                "Full name: " + name + " " + lastName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n";

        controller.showBookingConfirmation(bookingMessage);

    }

    public int getNbrOfTravelers() {
        return nbrOfTravelers;
    }

    public void setNbrOfTravelers(int nbrOfTravelers) {
        this.nbrOfTravelers = nbrOfTravelers;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
