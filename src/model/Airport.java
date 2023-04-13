package model;

public class Airport {

    private String name;

    private String airportCode;

    private String city;

    public Airport(String name, String airportCode, String city) {
        this.name = name;
        this.airportCode = airportCode;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
