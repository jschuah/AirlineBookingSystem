package ui;

import exception.InvalidPlaceException;
import model.Booking;
import model.Flight;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class BookingApp {

    private Scanner input;
    private String name;
    private int age;
    private String passport;
    private String command;
    private boolean flightClass;
    private String origin;
    private String destination;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/booking.json";
    private Booking nbooking;

    // EFFECTS: runs the Booking Application
    public BookingApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBooking();
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void runBooking() {

        boolean keepGoing = true;

        newScanner();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for considering Harimau Airlines!");
    }

    // MODIFIES: this
    // EFFECTS: initializes booking
    private void newScanner() {
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: Runs command
    private void processCommand(String command) {
        if (command.equals("a")) {
            newBooking();
        } else if (command.equals("s")) {
            saveBooking();
        } else if (command.equals("l")) {
            loadBooking();
        } else if (command.equals("g")) {
            generateBooking();
        } else {
            System.out.println("ERROR: NOT VALID...");
        }
    }


    // EFFECTS: Display a list of available options as the app's main menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> new booking");
        System.out.println("\ts -> save flight details to file");
        System.out.println("\tl -> load flight details from file");
        System.out.println("\tg -> generate and print booking");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Display a list of available options as the app's main menu
    private void displayClassMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tL -> Normal Lux Class");
        System.out.println("\tR -> Royal First Class");
    }

    // MODIFIES: this
    // EFFECTS: creates a new booking
    private void newBooking() {
        enterName();
        enterAge();
        enterPassport();
        infoCorrect();
        nbooking = new Booking(name, passport, age);


        while (wantAddFlight()) {
            //Return Boolean, False to break
            pickClass();
            pickOrigin();
            pickDestination();
            // Make sure flightClass is Boolean
            try {
                nbooking.addFlight(origin, destination, flightClass);
            } catch (InvalidPlaceException e) {
                System.out.println("Not a valid place!");
            }
        }


        generateBooking();


    }

    private void pickDestination() {
        System.out.println("Please type your destination");
        this.destination = input.nextLine();
    }

    private void pickOrigin() {
        System.out.println("Please type your origin");
        this.origin = input.nextLine();
    }

    private boolean wantAddFlight() {
        System.out.println("      ");
        System.out.println("Do you want to add a flight?");
        System.out.println("Y for yes, any other key for no");
        System.out.println("      ");

        String cmd = input.nextLine();

        if (cmd.equals("Y") || cmd.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Stores passenger's name
    private void enterName() {

        System.out.println("Please enter your full name (displayed on passport)");


        // Does it contain numbers?
        while ((this.name = input.nextLine()).matches(".*\\d.*")) {
            System.out.println("Please re-enter your full name, it must not contain numbers.");
        }


    }

    // MODIFIES: this
    // EFFECTS: Stores passenger's age
    private void enterAge() {

        while (true) {
            System.out.println("Please enter your age (as per to your birthdate on the passport)");

            int age = input.nextInt();

            if (age >= 100) {
                System.out.println("I don't think so...");
            } else if (age < 1) {
                System.out.println("I don't think so...");
            } else {
                this.age = age;
                input.nextLine();
                break;
            }
        }


    }


    // MODIFIES: this
    // EFFECTS: Stores passenger's passport number
    private void enterPassport() {
        while (true) {

            System.out.println("Please enter your 9 digit passport number");

            String passportTrial = input.nextLine();

            int length = passportTrial.length();

            if (length == 9) {
                this.passport = passportTrial;
                break;
            } else {
                System.out.println("NOT VALID, IT HAS TO BE 9 CHARACTERS LONG");
            }
        }

    }

    // EFFECTS: if y or Y, proceed to class selection.
    //          if any other character entered, all information must be re-entered.
    private void infoCorrect() {
        System.out.println("                                               ");
        System.out.println("Please read through the information that you've just entered");
        System.out.println("                                               ");
        System.out.println("=============================================================");
        System.out.println("Your full name is: " + name);
        System.out.println("Your age is: " + age);
        System.out.println("Your passport number is: " + passport);
        System.out.println("=============================================================");
        System.out.println("Is it correct?");
        System.out.println("These information MUST be accurate or you will not be allowed to board");
        System.out.println("                                               ");
        System.out.println("Y for yes, Enter any other key for no");

        String cmd = input.nextLine();

        if (cmd.equals("Y") || cmd.equals("y")) {
            System.out.println("Thank you for your information");
        } else {
            System.out.println("Please re-enter your information");
            newBooking();
        }

    }

    // MODIFIES: this
    // EFFECTS: Stores Passenger Class choice, either Normal Lux Class (L) or Royal First Class (R).
    private void pickClass() {
        System.out.println("Please pick your Harimau flight class");

        boolean keepGoing = true;

        while (keepGoing) {
            displayClassMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("L") || command.equals("l")) {
                keepGoing = false;
                this.flightClass = false;
                System.out.println("You have selected Normal Lux Class");

            } else if (command.equals("R") || command.equals("r")) {
                keepGoing = false;
                this.flightClass = true;
                System.out.println("You have selected Royal First Class");
            } else {
                System.out.println("Not available option");
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: Generates and Stores Passenger's Booking Code
    private void generateBooking() {

        System.out.println("Here's your booking code: " + nbooking.randomBookingCode());

        System.out.println("Please send this code with a photo of your passport to bookings@harimauair.com.my");

        ArrayList<Flight> flight = nbooking.getFlights();
        for (Flight flight1 : flight) {
            System.out.println("              ");
            System.out.println("Invidivual Flight Information");
            System.out.println("              ");
            System.out.println("Origin : " + flight1.getOrigin());
            System.out.println("Destination : " + flight1.getDestination());
            System.out.println("Flight Class : " + flight1.getFlightClass());
            System.out.println("               ");
        }

    }


    // EFFECTS: saves the booking to file
    private void saveBooking() {
        try {
            jsonWriter.open();
            jsonWriter.write(nbooking);
            jsonWriter.close();
            System.out.println("Saved " + nbooking.getBookingCode() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads booking from file
    private void loadBooking() {
        try {
            nbooking = jsonReader.read();
            System.out.println("Loaded " + nbooking.getBookingCode() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}



