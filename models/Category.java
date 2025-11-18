package models;

public class Category {
    private String name;
    private double dailyRentalFee;
    private int freeKmPerDay;
    private double extraKmCharge;
    private double taxRate;

    public Category(String name, double dailyRentalFee, int freeKmPerDay, double extraKmCharge, double taxRate) {
        this.name = name;
        this.dailyRentalFee = dailyRentalFee;
        this.freeKmPerDay = freeKmPerDay;
        this.extraKmCharge = extraKmCharge;
        this.taxRate = taxRate;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getDailyRentalFee() {
        return dailyRentalFee;
    }

    public int getFreeKmPerDay() {
        return freeKmPerDay;
    }

    public double getExtraKmCharge() {
        return extraKmCharge;
    }

    public double getTaxRate() {
        return taxRate;
    }


     // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDailyRentalFee(double dailyRentalFee) {
        this.dailyRentalFee = dailyRentalFee;
    }

    public void setFreeKmPerDay(int freeKmPerDay) {
        this.freeKmPerDay = freeKmPerDay;
    }

    public void setExtraKmCharge(double extraKmCharge) {
        this.extraKmCharge = extraKmCharge;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return String.format("%s - LKR %.2f/day, %d km free, LKR %.2f/km extra, Tax: %.0f%%",
                name, dailyRentalFee, freeKmPerDay, extraKmCharge, taxRate * 100);
    }
}
