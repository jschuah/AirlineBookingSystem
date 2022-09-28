import model.Booking;
import model.Flight;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight f1;
    private Flight f2;

    @Test
    public void testFlight() {

        f1 = new Flight("Kuala Lumpur", "Vancouver", true);
        f2 = new Flight("Vancouver", "London", false);

        assertEquals(f1.getOrigin(), "Kuala Lumpur");
        assertEquals(f2.getDestination(), "London");
        assertEquals(f1.getFlightClass(), "Royal First Class");
        assertEquals(f2.getFlightClass(), "Lux Regular Class");

    }


}
