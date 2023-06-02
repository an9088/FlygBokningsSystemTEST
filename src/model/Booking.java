package model;

import controller.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**

 *The Booking class represents a flight booking made by a user.
 *It stores information such as the booking number, number of travelers, name, address, email,
 *booking details, airport, and full name of the user.
 *The class provides methods for setting and getting the booking information, generating a unique booking ID,
 *and saving the booking information to a file.
 * @author Dino Patarcec
 * @author Mattias Malm
 */
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

    private AutomatedEmail autoEmail;

    /**
     * Constructs a Booking object for a signed-in user.
     *
     * @param fullName       the full name of the user
     * @param address        the address of the user
     * @param city           the city of the user
     * @param zip            the zip code of the user
     * @param country        the country of the user
     * @param email          the email address of the user
     * @param bookingNumber  the booking number
     * @param bookingDetails the booking details
     * @param airport        the airport for the booking
     * @param controller     the controller object
     */
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

    /**
     * Constructs a Booking object for a guest user.
     *
     * @param name           the first name of the user
     * @param lastName       the last name of the user
     * @param address        the address of the user
     * @param city           the city of the user
     * @param zip            the zip code of the user
     * @param country        the country of the user
     * @param email          the email address of the user
     * @param bookingNumber  the booking number
     * @param bookingDetails the booking details
     * @param airport        the airport for the booking
     * @param controller     the controller object
     */
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

    /**
     *Generates a booking confirmation message for a guest user.
     *@param name the first name of the user
     *@param lastName the last name of the user
     *@param address the address of the user
     *@param city the city of the user
     *@param zip the zip code of the user
     *@param country the country of the user
     *@param email the email address of the user
     *@param bookingDetails the booking details
     *@param airport the airport for the booking
     *@param bookingNumber the booking number
     */
    private void bookingConfirmation(String name, String lastName, String address, String city,
                                     String zip, String country, String email, String bookingDetails, String airport, int bookingNumber) {

        String bookingMessage = "Your booking number: " + bookingNumber + "\n\n" +
                "Thank you " + name + " " + lastName + " for using FlightBuddy for booking your flight tickets. " +
                "We hope you will have a pleasant stay in " + bookingDetails + "!\n\n" +
                "Here are your booking details for your upcoming trip:\n" + airport +
                "\nFull name: " + name + " " + lastName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n\n" +
                "Sincerely, the FlightBuddy team \n\n";

        controller.showBookingConfirmation(bookingMessage);
        saveBookingToFileForGuestUser(bookingMessage);
        autoEmail = new AutomatedEmail(email, bookingMessage);
    }

    /**
     *Sends a booking confirmation message to a signed-in user.
     *@param fullName The full name of the user.
     *@param address The address of the user.
     *@param city The city of the user.
     *@param zip The zip code of the user.
     *@param country The country of the user.
     *@param email The email address of the user.
     *@param airport The airport details for the upcoming trip.
     *@param bookingNumber The booking number assigned to the user.
     *@param bookingDetails The details of the booking for the upcoming trip.
     */
    private void bookingConfirmationForSignedInUser(String fullName, String address, String city, String zip,
                                                    String country, String email, String airport, int bookingNumber, String bookingDetails) {

        String bookingMessage = "Your booking number: " + bookingNumber + "\n\n" +
                "Thank you " + fullName + " for using FlightBuddy for booking your flight tickets. " +
                "We hope you will have a pleasant stay in " + bookingDetails + "!\n\n" +
                "Here are your booking details for your upcoming trip:\n" + airport +
                "\nFull name: " + fullName + "\n" +
                "Email address: " + email + "\n" +
                "Address: " + address + "\n" +
                "Zip code: " + zip + "\n" +
                "City: " + city + "\n" +
                "Country: " + country + "\n\n" +
                "Sincerely, the FlightBuddy team \n\n";

        controller.showBookingConfirmation(bookingMessage);
        saveBookingToFileForSignedInUser(email,bookingMessage);
        autoEmail = new AutomatedEmail(email, bookingMessage);

    }

    /**
     *Saves the booking details to a file for the signed-in user.
     *@param email The email address of the user.
     *@param bookingMessage The booking confirmation message to be saved.
     */
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

    /**
     * Generates a unique booking ID based on the current timestamp and a random number.
     *
     * @return The generated unique booking ID.
     */
    private String generateUniqueBookingId() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        return "BookingID: " + timestamp + "-" + randomNumber;
    }

    /**
     * Generates the booking title using a unique booking ID, full name, and booking number.
     *
     * @return The generated booking title.
     */
    public String generateBookingTitle() {
        String uniqueId = generateUniqueBookingId();
        String title = "[" + uniqueId + "] - " + getFullName() + " - [" + "Booking number: " + getBookingNumber() + "]";
        return title;
    }

    /**
     * Saves the booking details to a file for guest users.
     *
     * @param bookingMessage The booking confirmation message to be saved.
     */
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
