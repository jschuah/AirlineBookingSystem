package model;

import org.json.JSONObject;
import persistence.Writable;

public class Flight implements Writable {
    private String origin;
    private String destination;
    private String flightClass;
    private Boolean flightFirstClass;

    // MODIFIES: this
    // EFFECTS:  Creates new flight, with origin, destination and flight class Boolean.
    public Flight(String origin, String destination, Boolean flightFirstClass) {

        this.flightFirstClass = flightFirstClass;

        if (flightFirstClass) {
            this.flightClass = "Royal First Class";
        } else {
            this.flightClass = "Lux Regular Class";
        }

        this.origin = origin;
        this.destination = destination;

    }

    // EFFECTS: Returns Boolean Flying first class?
    public boolean getFlightFirstClass() {
        return flightFirstClass;
    }

    // EFFECTS: Returns Destination
    public String getDestination() {
        return this.destination;
    }

    // EFFECTS: Returns Origin
    public String getOrigin() {
        return this.origin;
    }

    // EFFECTS: Returns Flight Class
    public String getFlightClass() {
        return flightClass;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("origin", origin);
        json.put("destination", destination);
        json.put("flight class", flightFirstClass);
        return json;
    }


}
