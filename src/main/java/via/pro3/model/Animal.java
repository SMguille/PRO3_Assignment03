package via.pro3.model;

import java.time.LocalDate;

public class Animal {
    private int registrationNumber; // Renamed from 'id', but still int
    private LocalDate date;
    private double weight;
    private String origin;

    // Constructor with registrationNumber (for DB retrieval)
    public Animal(int registrationNumber, LocalDate date, double weight, String origin) {
        this.registrationNumber = registrationNumber;
        this.date = date;
        this.weight = weight;
        this.origin = origin;
    }

    // Constructor without registrationNumber (for creation)
    public Animal(LocalDate date, double weight, String origin) {
        this(0, date, weight, origin);
    }

    public Animal() {}

    public int getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(int registrationNumber) { this.registrationNumber = registrationNumber; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
}