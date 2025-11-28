package via.pro3.model;

import java.time.LocalDate;

public class Animal {
    private String registrationNumber;
    private LocalDate date;
    private double weight;
    private String origin;

    public Animal(String registrationNumber, LocalDate date, double weight, String origin) {
        this.registrationNumber = registrationNumber;
        this.date = date;
        this.weight = weight;
        this.origin = origin;
    }

    public Animal() {}

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}