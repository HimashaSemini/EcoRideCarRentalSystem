package models;

public class Customer {
    private String customerId;
    private String name;
    private String nicOrPassport;
    private String contactNumber;
    private String email;

    public Customer(String customerId, String name, String nicOrPassport, String contactNumber, String email) {
        this.customerId = customerId;
        this.name = name;
        this.nicOrPassport = nicOrPassport;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getNicOrPassport() {
        return nicOrPassport;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNicOrPassport(String nicOrPassport) {
        this.nicOrPassport = nicOrPassport;
    }

    public String getEmailAddress() {
        return email;
    }


    @Override
    public String toString() {
        return String.format("[%s] %s | NIC/Passport: %s | Contact: %s | Email: %s",
                customerId, name, nicOrPassport, contactNumber, email);
    }
}
