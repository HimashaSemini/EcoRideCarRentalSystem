package models;

import java.time.LocalDate;

public class Invoice {
    private String invoiceId;
    private Booking booking;
    private LocalDate issueDate;
    private double finalAmount;

    public Invoice(String invoiceId, Booking booking) {
        this.invoiceId = invoiceId;
        this.booking = booking;
    }

    // getters
    public String getInvoiceId() {
        return invoiceId;
    }

    public Booking getBooking() {
        return booking;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    // setters
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }


    @Override
    public String toString() {
        return String.format(
             "Invoice [%s] | Booking: %s | Customer: %s | Amount: LKR %.2f | Date: %s",
            invoiceId,
            booking.getBookingId(),
            booking.getCustomer().getName(),
            finalAmount,
            issueDate
        );
    }
}
