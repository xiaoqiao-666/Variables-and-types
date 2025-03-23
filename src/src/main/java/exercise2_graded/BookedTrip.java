package src.main.java.exercise2_graded;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class BookedTrip {
    public static final double TAX = 0.25;
    public static final double CHILD_DISCOUNT = 0.15;
    public static final double BREAKFAST_SURCHARGE = 0.05;
    public static final double SLEEPER_BERTH_SURCHARGE = 0.15; 
    public static final double PRIVATE_ENTERTAINMENT_SURCHARGE = 0.20;
    public static final double SHIP_TOUR_SURCHARGE = 0.0; 

    private String bookingID;
    private TripType tripType;
    private ArrayList<Passenger> passengers;
    private int totalPassengers;
    private int numberOfChildren;
    private LocalDate dateOfDeparture;
    private LocalDate dateOfReturn;
    private long totalDays;
    private double totalTicketPrice;

    public BookedTrip(String bookingID, TripType tripType, ArrayList<Passenger> passengers, int totalPassengers,
                     int numberOfChildren, LocalDate dateOfDeparture, LocalDate dateOfReturn) {
        this.bookingID = bookingID;
        this.tripType = tripType;
        this.passengers = new ArrayList<>(passengers);
        this.totalPassengers = totalPassengers;
        this.numberOfChildren = numberOfChildren;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfReturn = dateOfReturn;
        this.totalDays = ChronoUnit.DAYS.between(dateOfDeparture, dateOfReturn);
        this.totalTicketPrice = calculateTotalTicketPrice();
    }

    private double calculateTotalTicketPrice() {
        double basePrice = tripType.getBasePrice();
        double surcharges = basePrice * (BREAKFAST_SURCHARGE + SLEEPER_BERTH_SURCHARGE 
                            + PRIVATE_ENTERTAINMENT_SURCHARGE + SHIP_TOUR_SURCHARGE);
        double pricePerPassenger = basePrice + surcharges;
        double total = pricePerPassenger * (totalPassengers - numberOfChildren * CHILD_DISCOUNT);
        return calculatePriceAfterTax(total);
    }

    private double calculatePriceAfterTax(double price) {
        return price * (1 + TAX);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Thank you for flying with MarsX. Here’s your trip details:\n")
          .append("Booking ID: ").append(bookingID).append("\n")
          .append("Trip Type: ").append(tripType).append("\n")
          .append("Departure Date: ").append(dateOfDeparture).append("\n")
          .append("Return Date: ").append(dateOfReturn).append("\n")
          .append("Total Days: ").append(totalDays).append("\n")
          .append("Total Ticket Price: ").append(totalTicketPrice).append("\n")
          .append("Total Passengers: ").append(totalPassengers).append("\n")
          .append("Number of Children: ").append(numberOfChildren).append("\n")
          .append("Passengers:\n");
        for (Passenger p : passengers) {
            sb.append(p.toString()).append("\n");
        }
        sb.append("Have a wonderful and safe trip.");
        return sb.toString();
    }
}

