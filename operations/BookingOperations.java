package operations;

import managedata.DataManager;
import models.*;
import java.time.LocalDate;
import java.util.*;

public class BookingOperations {
    private List<Booking> bookings;
    private DataManager dataManager;
    private VehicleOperations vehicleOps;
    private CustomerOperations customerOps;
    private CategoryOperations categoryOps;
    private Scanner scanner = new Scanner(System.in);

    public BookingOperations(DataManager dataManager, VehicleOperations vehicleOps, CustomerOperations customerOps, CategoryOperations categoryOps) {
        this.dataManager = dataManager;
        this.vehicleOps = vehicleOps;
        this.customerOps = customerOps;
        this.categoryOps = categoryOps;
        this.bookings = dataManager.loadBookings(vehicleOps.getCars(), customerOps.getCustomers());
    }

    // CREATE NEW BOOKING 
    public void createBooking() {
        System.out.println("\n--- Create New Booking ---");

        System.out.print("Enter Booking ID: ");
        String bookingId = scanner.nextLine();

        //Customer Selection 
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();
        Customer customer = customerOps.findCustomerById(custId);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        // Category Selection
        Category category = categoryOps.chooseCategory();
        if (category == null) {
            System.out.println("Invalid category selection!");
            return;
        }

        System.out.println("\nAvailable Cars in '" + category.getName() + "':");
        List<Car> availableCars = new ArrayList<>();
        for (Car car : vehicleOps.getCars()) {
            if (car.getCategory().getName().equalsIgnoreCase(category.getName()) &&
                car.getAvailabilityStatus().equalsIgnoreCase("Available")) {
                availableCars.add(car);
                System.out.println("- " + car.getCarId() + " | " + car.getModel() + " | Daily Fee: LKR " + car.getCategory().getDailyRentalFee());
            }
        }

        if (availableCars.isEmpty()) {
            System.out.println("No available cars in this category right now.");
            return;
        }

        System.out.print("Enter Car ID to book: ");
        String carId = scanner.nextLine();
        Car selectedCar = findCarById(carId);

        if (selectedCar == null || !availableCars.contains(selectedCar)) {
            System.out.println("Invalid Car ID or Car not available.");
            return;
        }

        // Rental Schedule
        System.out.print("Enter rental start date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        LocalDate rentalStartDate;
        try {
            rentalStartDate = LocalDate.parse(dateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format!");
            return;
        }

        LocalDate today = LocalDate.now();

        //Must book at least 3 days in advance
        if (!rentalStartDate.isAfter(today.plusDays(2))) {
            System.out.println("Booking must be made at least 3 days in advance!");
            return;
        }

        // Rental Details 
        System.out.print("Enter number of rental days: ");
        int days = scanner.nextInt();
        System.out.print("Enter estimated kilometers to drive: ");
        double estimatedKm = scanner.nextDouble();
        scanner.nextLine(); 

        // Create Booking 
        Booking booking = new Booking(bookingId, customer, selectedCar, today, days, estimatedKm, estimatedKm);
        booking.setRentalStartDate(rentalStartDate);
        bookings.add(booking);
        selectedCar.setAvailabilityStatus("Reserved");

        // Save Data 
        dataManager.saveBookings(bookings);
        dataManager.saveCars(vehicleOps.getCars());

        System.out.println("\n Booking created successfully!");
        System.out.println("Category: " + category.getName());
        System.out.println("Rental Start Date: " + rentalStartDate);
        System.out.println("Base Price: LKR " + booking.calculateBasePrice());
    }

    // VIEW BOOKINGS 
    public void viewBookings() {
        System.out.println("\n--- All Bookings ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    //UPDATE BOOKING
    public void updateBooking() {
        System.out.print("Enter Booking ID to update: ");
        String bookingId = scanner.nextLine();
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        // Prevent updates after 2 days of booking
        LocalDate today = LocalDate.now();
        if (today.isAfter(booking.getBookingDate().plusDays(2))) {
            System.out.println("You cannot modify bookings after 2 days from reservation!");
            return;
        }

        System.out.print("Enter new estimated kilometers: ");
        double newKm = scanner.nextDouble();
        booking.setEstimatedKm(newKm);

        dataManager.saveBookings(bookings);
        System.out.println("Booking updated successfully!");
    }

    // CANCEL BOOKING
    public void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine();
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        // Prevent cancellation after 2 days
        LocalDate today = LocalDate.now();
        if (today.isAfter(booking.getBookingDate().plusDays(2))) {
            System.out.println("You cannot cancel bookings after 2 days from reservation!");
            return;
        }

        bookings.remove(booking);
        booking.getCar().setAvailabilityStatus("Available");
        dataManager.saveBookings(bookings);
        dataManager.saveCars(vehicleOps.getCars());

        System.out.println("Booking cancelled successfully.");
    }

    //FIND BOOKING BY ID
    public Booking findBookingById(String id) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

    // HELPER: FIND CAR
    private Car findCarById(String id) {
        for (Car c : vehicleOps.getCars()) {
            if (c.getCarId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }
}
