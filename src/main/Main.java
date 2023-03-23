package src.main;

public class Main {
    public static void main(String[] args) {

        Airline airline = new Airline("WizzAir", "W123AN1324");
        System.out.println("Added a new airline");
        System.out.println(airline.toString());
    }
}
