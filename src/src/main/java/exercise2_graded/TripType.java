package src.main.java.exercise2_graded;

public enum TripType {
    ROUND_TRIP(1000.0),
    ONE_WAY(500.0);

    private final double basePrice;

    TripType(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}

