package model;

import exception.InvalidPlaceException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Booking implements Writable {

    private String name;
    private int age;
    private String passport;
    private UUID bookingCode;
    private ArrayList<Flight> flightList;


    // REQUIRES: passengerName cannot contain Numbers and must be non-zero length.
    //           passportNum must be 9 digits long. Can be letter or Integer
    //           age must be integer bigger than 0 and smaller than 100
    // MODIFIES: this
    // EFFECTS:  Initiates Booking, stores passenger name,
    //           stores passport number and stores booking code

    public Booking(String passengerName, String passportNum, int age) {

        this.flightList = new ArrayList<Flight>();

        this.name = passengerName;
        this.passport = passportNum;
        this.age = age;

    }

    public Booking(UUID bookingCode, String passengerName, String passportNum, int age) {

        this(passengerName, passportNum, age);
        this.bookingCode = bookingCode;

    }

    // MODIFIES: this
    // EFFECTS:  Stores origin and destination of booking.
    //           If flightClass is 1, then it's Royal First Class.
    //           If FlightClass is 0, then it's Lux Regular Class.

    public void addFlight(String origin, String destination, Boolean flightFirstClass) throws InvalidPlaceException {

        Flight newFlight = new Flight(origin, destination, flightFirstClass);

        if ((origin.length() == 0) || (destination.length() == 0)) {
            throw new InvalidPlaceException("Invalid Place");
        }

        flightList.add(newFlight);


    }

    public ArrayList<Flight> getFlights() {
        return flightList;
    }

    // EFFECTS: Returns Passenger Name
    public String getPassengerName() {
        return name;
    }

    // EFFECTS: Returns Passenger Age
    public int getAge() {
        return age;
    }


    // MODIFIES: this
    // EFFECTS: Generates a random DOUBLE data type booking code and stores it.
    public UUID randomBookingCode() {
        this.bookingCode = UUID.randomUUID();
        return this.bookingCode;
    }

    // EFFECTS: Returns Booking Code
    public UUID getBookingCode() {
        return this.bookingCode;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("passport", passport);
        json.put("age", age);
        json.put("flights", flightsToJson());
        json.put("booking code", this.bookingCode);
        return json;
    }

    // EFFECTS: return flights in this booking as a JSON array
    private JSONArray flightsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Flight f : flightList) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }

}
