package model;

public class Airline {

    private String name;

    private String flightNumber;


    public Airline(String name, String flightNumber) {
        this.name = name;
        this.flightNumber = flightNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "name='" + name + '\'' +
                ", flightNumber='" + flightNumber + '\'' +
                '}';
    }
}
