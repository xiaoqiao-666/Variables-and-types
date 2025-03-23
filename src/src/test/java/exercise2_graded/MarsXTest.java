package exercise2_graded;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.lang.reflect.Method;

public class MarsXTest {

    private MarsXBookingSystem bookingSystem;
    private List<Passenger> passengers;
    private BookedTrip bookedTrip;
    private static final Random random = new Random();

    @Before
    public void setUp() {
        bookingSystem = new MarsXBookingSystem();
        passengers = new ArrayList<>();

        // Generate randomized passenger details
        String[] names = {"Balthazar", "Oedipus", "Zaphod", "Trillian", "Arthur", "Ford", "Marvin"};
        String name = names[random.nextInt(names.length)];
        LocalDate dob = LocalDate.of(random.nextInt(50) + 1970, random.nextInt(12) + 1, random.nextInt(28) + 1);
        String medicalCondition = random.nextBoolean() ? "None" : "Asthma";
        int insuranceNumber = random.nextInt(99999);
        String insuranceCompany = random.nextBoolean() ? "RedDOT" : "Far&Beyond";
        String insuranceContact = "555-" + (1000 + random.nextInt(9000));

        Passenger passenger = new Passenger("MX" + name.substring(0, 3).toUpperCase() + dob.getYear(),
                name, dob, medicalCondition, insuranceNumber, insuranceCompany, insuranceContact);
        passengers.add(passenger);

        // Generate random trip details
        TripType[] tripTypes = TripType.values();
        TripType randomTrip = tripTypes[random.nextInt(tripTypes.length)];
        LocalDate departure = LocalDate.of(2025, random.nextInt(12) + 1, random.nextInt(28) + 1);
        LocalDate returnDate = departure.plusDays(7 + random.nextInt(15)); // Ensuring at least 7 days stay

        bookedTrip = new BookedTrip("MX" + random.nextInt(99999), randomTrip, passengers, passengers.size(),
                random.nextInt(passengers.size()), departure, returnDate);
    }

    
    @Test
    public void testBookingSystemSRP() {
        assertNotNull("Booking System should have a list of passengers", bookingSystem.passengers);
        assertNull("Trip type should be initialized to null", bookingSystem.tripType);
    }

    
    @Test
    public void testPassengerAddition() {
        int initialSize = bookingSystem.passengers.size();
        bookingSystem.passengers.add(new Passenger("MXRND2025", "Random Name", LocalDate.of(2000, 1, 1), "None", 9999, "SpaceSafe", "555-7890"));
        assertEquals(initialSize + 1, bookingSystem.passengers.size());
    }

    
    @Test
    public void testPassengerRemoval() {
        Passenger tempPassenger = new Passenger("MXXYZ2023", "Temporary", LocalDate.of(1995, 6, 10), "Diabetes", 65432, "OrbitCare", "555-4321");
        bookingSystem.passengers.add(tempPassenger);
        bookingSystem.passengers.remove(tempPassenger);
        assertFalse(bookingSystem.passengers.contains(tempPassenger));
    }

    
    @Test
    public void testMinimumStayValidation() throws Exception {
        Method method = MarsXBookingSystem.class.getDeclaredMethod("moreThanSevenDays");
        method.setAccessible(true);

        bookingSystem.dateOfDeparture = LocalDate.of(2025, 10, 1);
        bookingSystem.dateOfReturn = LocalDate.of(2025, 10, 9); 

        boolean result = (boolean) method.invoke(bookingSystem);
        assertTrue("Trip must be at least 7 days", result);
    }

    
    @Test
    public void testBookingID() {
        assertTrue("Booking ID should start with MX", bookedTrip.getBookingID().startsWith("MX"));
    }

    
    @Test
    public void testTotalPassengers() {
        assertTrue("Passenger count should be at least 1", bookedTrip.getTotalPassengers() > 0);
    }

    
    @Test
    public void testTripDuration() {
        assertTrue("Total days should be greater than or equal to 7", bookedTrip.getTotalDays() >= 7);
    }

    
    @Test
    public void testHiddenPriceCalculation() throws Exception {
        Method method = BookedTrip.class.getDeclaredMethod("calculatePriceAfterTax", double.class);
        method.setAccessible(true);

        double basePrice = bookedTrip.getTripType().getBasePrice();
        double pricePerPassenger = basePrice + (basePrice * 0.05) + (basePrice * 0.15) + (basePrice * 0.2);
        double expectedPriceAfterTax = pricePerPassenger * 1.25;

        double actualPriceAfterTax = (double) method.invoke(bookedTrip, pricePerPassenger);
        assertEquals(expectedPriceAfterTax, actualPriceAfterTax, 0.01);
    }

    
    @Test
    public void testPassengerAgeCalculation() {
        Passenger testPassenger = passengers.get(0);
        Period agePeriod = Period.between(testPassenger.getDateOfBirth(), LocalDate.now());
        int expectedAge = agePeriod.getYears();
        assertEquals(expectedAge, testPassenger.getAge());
    }

    
    @Test
    public void testBookedTripToString() {
        assertTrue("Booking ID not found", bookedTrip.toString().contains("Booking ID: " + bookedTrip.getBookingID()));
    
        assertTrue("Total Days not found", bookedTrip.toString().contains("Total Days: " + bookedTrip.getTotalDays()));
    
        assertTrue("Total Ticket Price not found", bookedTrip.toString().contains("Total Ticket Price: " + bookedTrip.getTotalTicketPrice()));
    
        assertTrue("Total Passengers not found", bookedTrip.toString().contains("Total Passengers: " + bookedTrip.getTotalPassengers()));
    
        assertTrue("Number of Children not found", bookedTrip.toString().contains("Number of Children: " + bookedTrip.getNumberOfChildren()));
    }

    @Test
    public void testToStringContainsPassengerDetails() {
        for (Passenger passenger : passengers) {
            assertTrue("Passenger details missing", bookedTrip.toString().contains(passenger.toString()));
        }
    }
}
 
