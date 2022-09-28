package persistence;

import exception.InvalidPlaceException;
import model.Booking;
import model.Flight;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Booking bk = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testNoOriginException() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Booking bk = reader.read();
            bk.addFlight("", "Kul",true);
            fail("InvalidPlaceException expected");
        } catch (InvalidPlaceException | IOException e) {
            System.out.println("Pass!");
        }
    }

    @Test
    void testNoDestinationException() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Booking bk = reader.read();
            bk.addFlight("Van", "",false);
            fail("InvalidPlaceException expected");
        } catch (InvalidPlaceException | IOException e) {
            System.out.println("Pass!");
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBooking.json");
        try {
            Booking bk = reader.read();
            assertEquals("Sean", bk.getPassengerName());
            assertEquals(18, bk.getAge());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Couldn't read from file");
        }

    }


    @Test
    void testReaderGeneralBooking() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBooking.json");
        try {
            Booking bk = reader.read();
            assertEquals("Sean", bk.getPassengerName());
            List<Flight> flights = bk.getFlights();
            assertEquals(2, flights.size());
            checkFlight(flights.get(0),"KUL", "VAN", true);
            checkFlight(flights.get(1),"VAN", "SING", false);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Couldn't read from file");
        }
    }


}
