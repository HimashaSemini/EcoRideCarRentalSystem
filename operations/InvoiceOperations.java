package operations;

import managedata.DataManager;
import models.*;
import java.util.*;
import java.time.LocalDate;

public class InvoiceOperations {
    private List<Invoice> invoices;
    private List<Booking> bookings;
    private DataManager dataManager;

    public InvoiceOperations(List<Invoice> invoices, List<Booking> bookings, DataManager dataManager) {
        this.invoices = invoices;
        this.bookings = bookings;
        this.dataManager = dataManager;
    }

    // Generate Invoice
    public void generateInvoice() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Booking ID to generate invoice: ");
        String bookingId = sc.nextLine();

        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        // Ask for actual kilometers
        System.out.print("Enter actual kilometers driven: ");
        double actualKm = sc.nextDouble();
        sc.nextLine();
        
        booking.setActualKm(actualKm);

        // Calculate amounts
        
        double base = booking.calculateBasePrice();
        double extra = booking.calculateExtraKmCharge();
        double discount = booking.calculateDiscount(base);
        double subtotal = base + extra - discount;
        double tax = booking.calculateTax(subtotal);
        double finalAmount = booking.calculateFinalAmount(); 
        
        booking.setFinalAmount(finalAmount);
        

        Invoice invoice = new Invoice("INV-" + (invoices.size() + 1), booking);
        invoice.setFinalAmount(finalAmount);
        invoice.setIssueDate(LocalDate.now());
        invoices.add(invoice);

        dataManager.saveInvoices(invoices);
        dataManager.saveBookings(bookings); // Save updated actual km

        System.out.println("\n========== EcoRide Invoice ==========");
        System.out.println("Invoice ID      : " + invoice.getInvoiceId());
        System.out.println("Booking ID      : " + booking.getBookingId());
        System.out.println("Customer Name   : " + booking.getCustomer().getName());
        System.out.println("Car Model       : " + booking.getCar().getModel());
        System.out.println("Category        : " + booking.getCar().getCategory().getName());
        System.out.println("Days Rented     : " + booking.getRentalDays());
        System.out.println("Actual KM Driven: " + booking.getActualKm());
        System.out.println("-------------------------------------");
        System.out.printf("Base Price       : LKR %.2f%n", base);
        System.out.printf("Extra KM Charge  : LKR %.2f%n ", extra);
        System.out.printf("Discount (10%%)  : -LKR %.2f%n", discount);
        System.out.printf("Tax              : +LKR %.2f%n ", tax);
        System.out.println("Deposit Deducted: -LKR 5000.00");
        System.out.println("-------------------------------------");
        System.out.printf("FINAL PAYABLE   : LKR %.2f%n", finalAmount);
        System.out.println("-------------------------------------");
        System.out.println("Issue Date      : " + invoice.getIssueDate());
        System.out.println("=====================================");
    }

    // View All Invoices 
    public void viewInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("No invoices found.");
            return;
        }
        for (Invoice inv : invoices) {
            System.out.println(inv);
        }
    }

    //  Helper
    private Booking findBookingById(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) {
                return b;
            }
        }
        return null;
    }
}
