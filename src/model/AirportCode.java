package src.model;

public enum AirportCode {

    JFK("John F. Kennedy International Airport"),
    LAX("Los Angeles International Airport"),
    LHR("Heathrow Airport"),
    CDG("Charles de Gaulle Airport"),
    SYD("Sydney Airport"),
    NRT("Narita International Airport"),
    AMS("Amsterdam Airport Schiphol"),
    PEK("Beijing Capital International Airport"),
    DXB("Dubai International Airport"),
    CPH("Copenhagen Airport"),
    SIN("Changi Airport"),
    HKG("Hong Kong Airport"),
    FRA("Frankfurt Airport");


    private String airPortName;

    private AirportCode(String airPortName){

        this.airPortName = airPortName;
    }

    public String getAirPortName(){

        return airPortName;
    }
}
