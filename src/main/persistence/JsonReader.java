package persistence;

import exception.InvalidPlaceException;
import model.Booking;
import model.Flight;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader that reads bookings from JSON data stored in file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bookings from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Booking read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBooking(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses bookings from JSON object and returns it
    private Booking parseBooking(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String passport = jsonObject.getString("passport");
        int age = jsonObject.getInt("age");
        UUID bookingCode = UUID.fromString(jsonObject.getString("booking code"));
        Booking bk = new Booking(bookingCode, name, passport, age);
        addFlights(bk, jsonObject);
        return bk;
    }

    // MODIFIES: bk
    // EFFECTS: parses flights from JSON object and adds them to booking
    private void addFlights(Booking bk, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("flights");
        for (Object json : jsonArray) {
            JSONObject nextFlight = (JSONObject) json;
            addFlight(bk, nextFlight);
        }
    }

    // MODIFIES: bk
    // EFFECTS: parses flight from JSON object and adds it to booking
    private void addFlight(Booking bk, JSONObject jsonObject) {
        String origin = jsonObject.getString("origin");
        String destination = jsonObject.getString("destination");
        Boolean flightFirstClass = jsonObject.getBoolean("flight class");

        try {
            bk.addFlight(origin, destination, flightFirstClass);
        } catch (InvalidPlaceException e) {
            e.printStackTrace();
        }

    }


}
