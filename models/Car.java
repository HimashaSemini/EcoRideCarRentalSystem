package models;

public class Car {
    private String carId;
    private String model;
    private Category category;
    private double dailyRentalPrice;
    private String availabilityStatus; // Available, Reserved, Under Maintenance

    public Car(String carId, String model, Category category, double dailyRentalPrice, String availabilityStatus) {
        this.carId = carId;
        this.model = model;
        this.category = category;
        this.dailyRentalPrice = dailyRentalPrice;
        this.availabilityStatus = availabilityStatus;
    }

    // Getters
    public String getCarId() {
        return carId;
    }

    public String getModel() {
        return model;
    }

    public Category getCategory() {
        return category;
    }

    public double getTaxRate(){
        return category.getTaxRate();
    }

    public double getExtraKmCharge(){
        return category.getExtraKmCharge();
    }

    public int getFreeKmPerDay(){
        return category.getFreeKmPerDay();
    }

    public double getDailyRentalPrice() {
        return dailyRentalPrice;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    // Setters
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setDailyRentalPrice(double dailyRentalPrice) {
        this.dailyRentalPrice = dailyRentalPrice;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Category: %s | LKR %.2f/day | Status: %s",
                carId, model, category.getName(), dailyRentalPrice, availabilityStatus);
    }
}
