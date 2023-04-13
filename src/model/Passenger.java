package model;

import java.util.ArrayList;

public class Passenger {

    private String firstName;

    private String lastName;

    private String socialSecurityNbr;

    private String address;

    private ArrayList<Passenger> passengers = new ArrayList<Passenger>();

    public Passenger(String firstName, String lastName, String socialSecurityNbr, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNbr = socialSecurityNbr;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSocialSecurityNbr() {
        return socialSecurityNbr;
    }

    public void setSocialSecurityNbr(String socialSecurityNbr) {
        this.socialSecurityNbr = socialSecurityNbr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", socialSecurityNbr='" + socialSecurityNbr + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
