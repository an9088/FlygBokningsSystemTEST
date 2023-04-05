package src.model;

import java.util.ArrayList;

public class Booking {

    private String reservationNumber;
    private int nbrOfTravelers;

    private ArrayList bookings = new ArrayList();
    private String name;
    private String lastName;
    private String address;
    private String zip;
    private String country;
    private String city;
    private String email;

    /*public Booking(String reservationNumber) {
        this.reservationNumber = reservationNumber;
        this.nbrOfTravelers = nbrOfTravelers;
    }

     */



    public Booking(String name, String lastName, String address, String city, String zip, String country, String email) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.email = email;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
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
