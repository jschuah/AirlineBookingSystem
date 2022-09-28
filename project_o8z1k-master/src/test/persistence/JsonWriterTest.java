package persistence;

import exception.InvalidPlaceException;
import model.Booking;
import model.Flight;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Booking bk = new Booking("Sean", "A38444656", 18);
            bk.randomBookingCode();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IO Exception was expected");
        } catch (FileNotFoundException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyBooking() {
        try {
            Booking bk = new Booking("Sean", "A38444656", 18);
            bk.randomBookingCode();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBooking.json");
            writer.open();
            writer.write(bk);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBooking.json");
            bk = reader.read();
            assertEquals("Sean", bk.getPassengerName());
            assertEquals(18, bk.getAge());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBooking() {
        try {
            Booking bk = new Booking("Sean", "A38444656", 18);
            bk.randomBookingCode();
            try {
                bk.addFlight("KUL", "VAN", true);
            } catch (InvalidPlaceException e) {
                fail();
            }
            try {
                bk.addFlight("VAN", "SING", false);
            } catch (InvalidPlaceException e) {
                fail();
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBooking.json");
            writer.open();
            writer.write(bk);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBooking.json");
            bk = reader.read();
            assertEquals("Sean", bk.getPassengerName());
            assertEquals(18, bk.getAge());
            List<Flight> flights = bk.getFlights();
            assertEquals(2, flights.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



}
