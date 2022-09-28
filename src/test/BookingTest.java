import exception.InvalidPlaceException;
import model.Booking;
import model.Flight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    private Booking b1;
    private Booking b2;
    private Booking b3;

    @BeforeEach
    public void setup() {
        b1 = new Booking("Chuah Jun Sean", "A38444656", 18);
        b2 = new Booking("Eleonora Mezzo", "ITALY1234", 19);
        b3 = new Booking("Oscar Cheng", "HK12356", 19);
    }

    @Test
    public void testAddBooking() {

        try {
            b1.addFlight("Kuala Lumpur", "Vancouver", true);
        } catch (InvalidPlaceException e) {
            fail();
        }
        try {
            b1.addFlight("Singapore", "Rome", false);
        } catch (InvalidPlaceException e) {
            fail();
        }

        assertEquals(b1.getFlights().get(0).getFlightClass(), "Royal First Class");
        assertEquals(b1.getFlights().get(1).getFlightClass(), "Lux Regular Class");

    }

    @Test
    public void testEmptyPlace() {

        try {
            b1.addFlight("", "Vancouver", true);
            fail();
        } catch (InvalidPlaceException e){
            System.out.println("PASS!");
        }

        try {
            b2.addFlight("Kuala Lumpur", "", false);
            fail();
        } catch (InvalidPlaceException e){
            System.out.println("Pass!");
        }

    }

    @Test
    public void testRandomBookingCode() {

        UUID first = b3.randomBookingCode();
        UUID second = b3.randomBookingCode();

        assertNotEquals(second, first);
        assertEquals(second, b3.getBookingCode());

    }

    @Test
    public void getPassenger() {

        assertEquals(b1.getPassengerName(), "Chuah Jun Sean");
        assertEquals(b2.getPassengerName(), "Eleonora Mezzo");


    }

    @Test
    public void getAge() {

        assertEquals(b1.getAge(), 18);
        assertEquals(b3.getAge(), 19);


    }




}
