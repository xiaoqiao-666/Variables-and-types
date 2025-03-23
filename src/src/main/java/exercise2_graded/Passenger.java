package src.main.java.exercise2_graded;

import java.time.LocalDate;
import java.time.Period;

public class Passenger {
    private String passengerID;
    private String passengerName;
    private LocalDate dateOfBirth;
    private int age;
    private String knownMedicalConditions;
    private int insuranceNumber;
    private String insuranceCompany;
    private String insuranceContactNumber;

    public Passenger(String passengerID, String passengerName, LocalDate dateOfBirth, String knownMedicalConditions,
                     int insuranceNumber, String insuranceCompany, String insuranceContactNumber) {
        this.passengerID = passengerID;
        this.passengerName = passengerName;
        this.dateOfBirth = dateOfBirth;
        this.knownMedicalConditions = knownMedicalConditions;
        this.insuranceNumber = insuranceNumber;
        this.insuranceCompany = insuranceCompany;
        this.insuranceContactNumber = insuranceContactNumber;
        this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Passenger ID: " + passengerID + "\n"
             + "Passenger Name: " + passengerName + "\n"
             + "Date of Birth: " + dateOfBirth + "\n"
             + "Age: " + age + "\n"
             + "Medical Conditions: " + knownMedicalConditions + "\n"
             + "Insurance Number: " + insuranceNumber + "\n"
             + "Insurance Company: " + insuranceCompany + "\n"
             + "Insurance Contact: " + insuranceContactNumber;
    }

    public int getAge() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAge'");
    }
}
