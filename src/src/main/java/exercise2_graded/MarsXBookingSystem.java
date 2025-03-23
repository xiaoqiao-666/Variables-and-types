package src.main.java.exercise2_graded;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class MarsXBookingSystem {
    public TripType tripType;
    public LocalDate dateOfDeparture;
    public LocalDate dateOfReturn;
    public ArrayList<Passenger> passengers = new ArrayList<>();

    public void startBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available trip types:");
        for (TripType type : TripType.values()) {
            System.out.println((type.ordinal() + 1) + ". " + type.name());
        }
        System.out.print("Select trip type: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        tripType = TripType.values()[choice - 1];
        passengerRegistration();
    }

    private void passengerRegistration() {
        Scanner scanner = new Scanner(System.in);
        boolean addMore = true;
        while (addMore) {
            System.out.print("Enter passenger name: ");
            String name = scanner.nextLine();
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());
            System.out.print("Medical conditions: ");
            String medical = scanner.nextLine();
            System.out.print("Insurance number: ");
            int insNumber = Integer.parseInt(scanner.nextLine());
            System.out.print("Insurance company: ");
            String insCompany = scanner.nextLine();
            System.out.print("Insurance contact: ");
            String insContact = scanner.nextLine();

            String passengerID = "MX" + 
                (name.length() >= 3 ? name.substring(0,3) : name).toUpperCase() 
                + dob.getYear();
            passengers.add(new Passenger(passengerID, name, dob, medical, insNumber, insCompany, insContact));

            System.out.print("Add another passenger? (yes/no): ");
            addMore = scanner.nextLine().equalsIgnoreCase("yes");
        }

        System.out.print("Departure date (YYYY-MM-DD): ");
        dateOfDeparture = LocalDate.parse(scanner.nextLine());
        System.out.print("Return date (YYYY-MM-DD): ");
        dateOfReturn = LocalDate.parse(scanner.nextLine());

        if (!moreThanSevenDays()) {
            System.out.println("Return date must be at least 7 days after departure");
            return;
        }

        int childrenCount = (int) passengers.stream().filter(p -> p.getAge() < 18).count();
        LocalDateTime now = LocalDateTime.now();
        String bookingID = String.format("MX%02d%04d%02d%02d",
                passengers.size(), now.getYear(), now.getHour(), now.getMinute());

        BookedTrip trip = new BookedTrip(bookingID, tripType, passengers, passengers.size(), childrenCount,
                dateOfDeparture, dateOfReturn);
        System.out.println(trip);
    }

    private boolean moreThanSevenDays() {
        return ChronoUnit.DAYS.between(dateOfDeparture, dateOfReturn) >= 7;
    }
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
}
