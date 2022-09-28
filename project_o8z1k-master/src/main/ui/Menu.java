package ui;

import exception.InvalidPlaceException;
import model.Booking;
import model.Flight;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Menu implements Runnable {

    private Booking nbooking;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/booking.json";

    private String name;
    private int age;
    private String passport;
    private String yn;
    private String origin;
    private String destination;
    private boolean flightClass;

    private JFrame frame;
    private JButton newBooking;
    private JButton quit;
    private JButton save;
    private JButton load;
    private JButton generate;

    private JFrame newFrame;
    private JFrame flightAdd;
    private JFrame bookingScreen;

    private JPanel bookingInformation;

    // MODIFIES: this
    // EFFECTS: Shows popup dialog asking for name input
    public void askName() {
        name = JOptionPane.showInputDialog(frame,
                "Please enter your full name (displayed on passport)", null);
    }

    // REQUIRES: input age must be an integer
    // MODIFIES: this
    // EFFECTS: Shows popup dialog asking for age input
    public void askAge() {
        String stringAge;
        stringAge = JOptionPane.showInputDialog(frame,
                "Please enter your age (as per to your birthdate on the passport)", null);
        this.age = Integer.parseInt(stringAge);
    }

    // MODIFIES: this
    // EFFECTS: Shows popup dialog asking for passport input
    public void askPassport() {
        passport = JOptionPane.showInputDialog(frame,
                "Please enter your 9 digit passport number", null);
    }


    // EFFECTS: Asks to confirm entered information in a series of popups. If y or Y is entered, program can proceed.
    //          Else, booking has to be re-made.
    public void infoCorrect() {
        JOptionPane.showMessageDialog(frame,
                "Please confirm your entered information. If there is an error, you wont be allowed to board.");
        JOptionPane.showMessageDialog(frame,
                "Enter y or Y to confirm and any other key to re-enter info. INFORMATION comes in NEXT popup!");
        yn = JOptionPane.showInputDialog(frame,
                "Your Name: " + name + " ," + "Your Age: " + age + " , " + "Passport Num: " + passport + ".",
                null);

        if (yn.equals("Y") || yn.equals("y")) {
            JOptionPane.showMessageDialog(frame, "Thank you for your information");
        } else {
            JOptionPane.showMessageDialog(frame, "Please create a new booking and re-enter your information");
            run();
        }
    }


    // EFFECTS: Runs the corresponding methods in order, in charge of booking the flight and the main function of
    //          the program.
    public void onNewBookingPress(ActionEvent e) {
        bookingInformation.setVisible(true);

        askName();
        appropriateName();
        askAge();
        appropriateAge();
        askPassport();
        appropriateLength();

        infoCorrect();

        nbooking = new Booking(name, passport, age);

        while (wantAddFlight()) {
            pickOrigin();
            pickDestination();
            pickClass();
            try {
                nbooking.addFlight(origin, destination, flightClass);
            } catch (InvalidPlaceException invalidPlaceException) {
                JOptionPane.showMessageDialog(frame, "Not a valid place!");
            }
        }

        generateBooking();

    }

    // MODIFIES: this
    // EFFECTS: Popup dialog asking for input for the flight class. L/l for normal lux class, R/r for royal first class.
    //          Else, re-enter information.
    public void pickClass() {
        boolean keepGoing = true;

        while (keepGoing) {

            String planeClass = JOptionPane.showInputDialog(frame,
                    "Please select your flight class. L for the Lux regular class, R for the royal first class",
                    null);

            if (planeClass.equals("L") || planeClass.equals("l")) {
                this.flightClass = false;
                JOptionPane.showMessageDialog(frame, "You have selected Normal Lux Class");
                keepGoing = false;
            } else if (planeClass.equals("R") || planeClass.equals("r")) {
                this.flightClass = true;
                JOptionPane.showMessageDialog(frame, "You have selected Royal First Class");
                keepGoing = false;
            } else {
                JOptionPane.showMessageDialog(frame, "Not available option. Please re-enter");
            }
        }
    }

    // EFFECTS: Prints out all the flights and it's corresponding info from a booking with it's specific booking code.
    public void generateBooking() {
        bookingScreen = new JFrame();
        bookingScreen.setVisible(true);
        bookingScreen.setLayout(new GridLayout(5, 5));
        bookingScreen.add(new JLabel("Here's your booking code: " + nbooking.randomBookingCode()));
        bookingScreen.add(new JLabel("Please send this code with your passport photo to bookings@harimauair.my"));
        ArrayList<Flight> flight = nbooking.getFlights();
        for (Flight flight1 : flight) {
            bookingScreen.add(new JLabel("           "));
            bookingScreen.add(new JLabel("Individual Flight Information"));
            bookingScreen.add(new JLabel("           "));
            bookingScreen.add(new JLabel("Origin: " + flight1.getOrigin()));
            bookingScreen.add(new JLabel("Destination: " + flight1.getDestination()));
            bookingScreen.add(new JLabel("Flight Class: " + flight1.getFlightClass()));
            bookingScreen.add(new JLabel("           "));
        }
    }

    // EFFECTS: Popup window asking whether or not user wants to add a flight. Y/y for yes, else  not.
    public boolean wantAddFlight() {
        String want = JOptionPane.showInputDialog(frame,
                "Do you want to add a flight? Y or y for yes and any other key for no. ",
                null);

        if (want.equals("Y") || want.equals("y")) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows popup dialog asking for origin input
    public void pickOrigin() {
        origin = JOptionPane.showInputDialog(frame,
                "Please enter your origin.",
                null);
    }

    // MODIFIES: this
    // EFFECTS: Shows popup dialog asking for destination input
    public void pickDestination() {
        destination = JOptionPane.showInputDialog(frame,
                "Please enter your destination.",
                null);
    }

    // EFFECTS: Checks if entered passport number/code's size is exactly 9. Else re-enter.
    public void appropriateLength() {
        while (passport.length() != 9) {
            JOptionPane.showMessageDialog(frame,
                    "NOT VALID, IT HAS TO BE 9 CHARACTERS LONG");
            askPassport();
        }
    }

    // EFFECTS: Checks if entered name is just a string of letters. Else re-enter.
    public void appropriateName() {
        while (name.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(frame,
                    "Please re-enter your full name, it must not contain numbers.");
            askName();
        }
    }

    // EFFECTS: Checks if entered age is realistic between 1 and 100. Else re-enter.
    public void appropriateAge() {
        while ((age >= 100) | (age < 1)) {
            JOptionPane.showMessageDialog(frame,
                    "I don't think so...");
            askAge();
        }
    }

    // EFFECTS: Quit program
    public void onQuitPress(ActionEvent e) {
        System.exit(0);
    }

    // REQUIRES: A booking to have been made
    // MODIFIES: Save File
    // EFFECTS: Save the previously made booking.
    public void onSavePress(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "Your bookings have been saved!");
        try {
            jsonWriter.open();
            jsonWriter.write(nbooking);
            jsonWriter.close();
            System.out.println("Saved " + nbooking.getBookingCode() + " to " + JSON_STORE);
        } catch (FileNotFoundException e1) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // REQUIRES: A booking has to be previously saved.
    // MODIFIES: this
    // EFFECTS: Loads the saved booking.
    public void onLoadPress(ActionEvent e) {
        JOptionPane.showMessageDialog(frame, "Your bookings have been loaded!");
        try {
            nbooking = jsonReader.read();
            System.out.println("Loaded " + nbooking.getBookingCode() + " from " + JSON_STORE);
        } catch (IOException e1) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Calls generateBooking method.
    public void onGeneratePress(ActionEvent e) {
        generateBooking();
    }

    // EFFECTS: Display an image on the screen
    public void displayImage() throws IOException {
        BufferedImage gambar = ImageIO.read(new File("./data/petronas-twin-towers.jpeg"));
        JLabel picLabel = new JLabel(new ImageIcon(gambar));
        frame.add(picLabel);
    }

    // EFFECTS: Initialises the Json
    public void initialiseJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: The introduction labels to the program in the main menu.
    public void startingLabels() {
        frame.add(new JLabel("Welcome to the Malaysian Harimau Airways booking System!"));
        frame.add(new JLabel("Select from: "));
    }

    // EFFECTS: The look of the main menu including each button's corresponding actions.
    @Override
    public void run() {
        // Create the window
        frame = new JFrame("Malaysian Harimau Airways");
        initialiseJson();
        bookingInformation = new JPanel();

        // Sets the behavior for when the window is closed
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Add a layout manager so that the button is not placed on top of the label
        frame.setLayout(new GridLayout(7, 7));
        // Add a label and a button
        startingLabels();

        frame.add(newBooking = new JButton("New Booking"));
        newBooking.addActionListener(this::onNewBookingPress);

        try {
            displayImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(save = new JButton("Save Flight Details to File"));
        save.addActionListener(this::onSavePress);

        frame.add(load = new JButton("Load Flight Details From File"));
        load.addActionListener(this::onLoadPress);

        frame.add(generate = new JButton("Generate and Print Booking"));
        generate.addActionListener(this::onGeneratePress);

        frame.add(quit = new JButton("quit"));
        quit.addActionListener(this::onQuitPress);


        // Arrange the components inside the window
        frame.pack();
        // By default, the window is not visible. Make it visible.
        frame.setVisible(true);
    }

    // Method that runs everything
    public static void main(String[] args) {
        Menu se = new Menu();
        // Schedules the application to be run at the correct time in the event queue.
        SwingUtilities.invokeLater(se);
    }

}