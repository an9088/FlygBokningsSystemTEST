package model;

import controller.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Booking {

    private int bookingNumber;
    private int nbrOfTravelers;

    private Controller controller;

    private String name;
    private String lastName;
    private String address;
    private String zip;
    private String country;
    private String city;
    private String email;
    private String bookingDetails;
    private String airport;
    private String fullName;

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

    public int getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(int bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public int getNbrOfTravelers() {
        return nbrOfTravelers;
    }

    public void setNbrOfTravelers(int nbrOfTravelers) {
        this.nbrOfTravelers = nbrOfTravelers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private void bookingConfirmation(String name, String lastName, String address, String city,
                                     String zip, String country, String email, String bookingDetails, String airport, int bookingNumber) {
        String bookingMessage = "Your booking number: " + bookingNumber + "\n\n" +
                "Thank you " + name + " " + lastName + " for using this application for booking your flight tickets. \n" +
                "We hope you will have a pleasant stay in " + bookingDetails + "\n" +
                "Here are your booking details for your upcoming trip:\n\n" + airport +
                "\nFull name: " + name + " " + lastName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n";

        controller.showBookingConfirmation(bookingMessage);
        saveBookingToFileForGuestUser(bookingMessage);
    }

    private void bookingConfirmationForSignedInUser(String fullName, String address, String city, String zip,
                                                    String country, String email, String airport, int bookingNumber, String bookingDetails) {
        String bookingMessage = "Your booking number: " + bookingNumber + "\n\n" +
                "Thank you " + fullName + " for using this application for booking your flight tickets. \n" +
                "We hope you will have a pleasant stay in " + bookingDetails + "\n" +
                "Here are your booking details for your upcoming trip:\n\n" + airport +
                "\nFull name: " + fullName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n";

        controller.showBookingConfirmation(bookingMessage);
        saveBookingToFileForSignedInUser(email,bookingMessage);
    }

    private void saveBookingToFileForSignedInUser(String email, String bookingMessage) {
        String fileName = email + ".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.newLine();
            writer.write(">>> " + generateBookingTitle() + " <<<"); // Delimiter with generated title
            writer.newLine();
            writer.write(bookingMessage);
            writer.newLine();
            writer.write("=== END OF BOOKING ==="); // Delimiter
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save booking to file: " + e.getMessage());
        }
    }



    private String generateUniqueBookingId() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        return "BookingID: " + timestamp + "-" + randomNumber;
    }

    public String generateBookingTitle() {
        String uniqueId = generateUniqueBookingId();
        String title = "[" + uniqueId + "] - " + getFullName() + " - [" + "Booking number: " + getBookingNumber() + "]";
        return title;
    }


    private void saveBookingToFileForGuestUser(String bookingMessage) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("guest_bookings.txt", true));
            writer.write(bookingMessage);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save booking to file" + e.getMessage());
        }
    }
}
