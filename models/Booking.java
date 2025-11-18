package models;

import java.time.LocalDate;

public class Booking {
    private String bookingId;
    private Customer customer;
    private Car car;
    private LocalDate bookingDate;
    private LocalDate rentalStartDate;
    private int rentalDays;
    private double estimatedKm;
    private double actualKm;
    private double deposit = 5000.0; // Refundable deposit
    private double finalAmount;
    

    // Constructor
    public Booking(String bookingId, Customer customer, Car car, LocalDate bookingDate, int rentalDays, double estimatedKm, double actualKm) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.car = car;
        this.bookingDate = bookingDate;
        this.rentalDays = rentalDays;
        this.estimatedKm = estimatedKm;
        this.actualKm = actualKm;
    }

    // Calculation Methods

    //Base Price
    public double calculateBasePrice() {
        return car.getCategory().getDailyRentalFee() * rentalDays;
    }
   
    // Extra KM fee based on ACTUAL distance
    public double calculateExtraKmCharge() {
        double freeKmTotal = car.getCategory().getFreeKmPerDay() * rentalDays;
        double extraKm = actualKm - freeKmTotal;
        
        System.out.println("Actual KM: " + actualKm);
        System.out.println("Free KM Total: " + freeKmTotal);
        System.out.println("Extra KM: " + extraKm);

        if (extraKm > 0) {
            return extraKm * car.getCategory().getExtraKmCharge();
        } else {
            return 0.0;
        }
 }

   // 10% discount for 7 or more rental days
    public double calculateDiscount(double basePrice) {
        if (rentalDays >= 7) {
            return basePrice * 0.10; // 10% discount
        }
        return 0.0;
    }

    // Tax Calculation
    public double calculateTax(double subtotal) {
        return subtotal * car.getCategory().getTaxRate();
    }

    // Final Amount Calculation
    public double calculateFinalAmount() {
        double base = calculateBasePrice();
        double extra = calculateExtraKmCharge();
        double discount = calculateDiscount(base);
        double subtotal = base + extra - discount;
        double tax = calculateTax(subtotal);
        finalAmount = subtotal + tax - deposit;
        return finalAmount;
    }

    // Getters and Setters

    public String getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public double getEstimatedKm() { 
        return estimatedKm; 
    }

    public void setEstimatedKm(double estimatedKm) {
    this.estimatedKm = estimatedKm;
    }

    public double getActualKm() { 
        return actualKm; 
    }

    public void setActualKm(double actualKm) { 
        this.actualKm = actualKm; 
    }

    public double getFinalAmount() {
        return finalAmount;
    }
    
    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
    


    @Override
    public String toString() {
        return String.format(
            "Booking [%s]: %s rented %s (%s) | Days: %d | Est KM: %.1f | Actual KM: %.1f | Final: LKR %.2f",
            bookingId, 
            customer.getName(),
            car.getModel(),
            car.getCategory().getName(),
            rentalDays,
            estimatedKm,
            actualKm,
            finalAmount
        );
    }
}
