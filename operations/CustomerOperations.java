package operations;

import models.Customer;
import managedata.DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerOperations {
    private List<Customer> customers;
    private DataManager dataManager;
    private Scanner scanner = new Scanner(System.in);

    public CustomerOperations(DataManager dataManager) {
        this.dataManager = dataManager;
        this.customers = dataManager.loadCustomers();
    }

    // REGISTER CUSTOMER
    public void registerCustomer() {
        System.out.println("\n--- Register New Customer ---");
        System.out.print("Enter Customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter NIC or Passport: ");
        String nic = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(id, name, nic, contact, email);
        customers.add(customer);
        dataManager.saveCustomers(customers);

        System.out.println("Customer registered successfully!");
    }

    //Update Customer
    public void updateCustomer() {
        System.out.println("\n--- Update Customer Details ---");
        System.out.print("Enter Customer ID to update: ");
        String id = scanner.nextLine();

        Customer customer = findCustomerById(id);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.print("Enter new Full Name (current: " + customer.getName() + "): ");
        customer.setName(scanner.nextLine());
        System.out.print("Enter new NIC or Passport (current: " + customer.getNicOrPassport() + "): ");
        customer.setNicOrPassport(scanner.nextLine());
        System.out.print("Enter new Contact Number (current: " + customer.getContactNumber() + "): ");
        customer.setContactNumber(scanner.nextLine());
        System.out.print("Enter new Email Address (current: " + customer.getEmailAddress() + "): ");
        customer.setEmail(scanner.nextLine());

        dataManager.saveCustomers(customers);
        System.out.println("Customer details updated successfully!");
    }

    //Delete Customer
    public void deleteCustomer() {
        System.out.println("\n--- Delete Customer ---");
        System.out.print("Enter Customer ID to delete: ");
        String id = scanner.nextLine();

        Customer customer = findCustomerById(id);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        customers.remove(customer);
        dataManager.saveCustomers(customers);
        System.out.println("Customer deleted successfully!");
    }

    // VIEW CUSTOMERS 
    public void viewCustomers() {
        System.out.println("\n--- Registered Customers ---");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    // SEARCH CUSTOMER 
    public void searchCustomer() {
        System.out.println("\n--- Search Customer ---");
        System.out.print("Enter Customer ID to search: ");
        String id = scanner.nextLine();

        Customer customer = findCustomerById(id);
        if (customer == null) {
            System.out.println("Customer not found!");
        } else {
            System.out.println("Customer Details:");
            System.out.println(customer);
        }
    }
    //FIND CUSTOMER BY ID 
    public Customer findCustomerById(String id) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // GETTER FOR OTHER CLASSES TO ACCESS CUSTOMERS-
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }
}
