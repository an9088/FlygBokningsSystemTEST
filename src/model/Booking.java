package src.model;

import java.util.ArrayList;

public class Booking {

    private String reservationNumber;

    private int nbrOfTravelers = 0;

    private ArrayList bookings = new ArrayList();

    public Booking(String reservationNumber, int nbrOfTravelers) {
        this.reservationNumber = reservationNumber;
        this.nbrOfTravelers = nbrOfTravelers;
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
}
