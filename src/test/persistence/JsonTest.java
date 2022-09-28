package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkFlight(Flight testFlight, String origin, String destination, Boolean flightFirstClass) {

        assertEquals(origin, testFlight.getOrigin());
        assertEquals(destination, testFlight.getDestination());
        assertEquals(flightFirstClass, testFlight.getFlightFirstClass());

    }
}
