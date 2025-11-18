package operations;

import managedata.DataManager;
import java.util.Scanner;

public class Menus {
    private VehicleOperations vehicleOps;
    private CustomerOperations customerOps;
    private BookingOperations bookingOps;
    private InvoiceOperations invoiceOps;
    private CategoryOperations categoryOps;
    private DataManager dataManager;
    
    private Scanner scanner = new Scanner(System.in);

    public Menus() {
        dataManager = new DataManager();

        categoryOps = new CategoryOperations(dataManager); 
        vehicleOps = new VehicleOperations(dataManager, categoryOps);
        customerOps = new CustomerOperations(dataManager);
        bookingOps = new BookingOperations(dataManager, vehicleOps, customerOps, categoryOps);
        invoiceOps = new InvoiceOperations(dataManager.loadInvoices(bookingOps.getBookings()),bookingOps.getBookings(),dataManager);
    }

    // MAIN MENU 
    public void startSystem() {
        int choice;
        do {
            showMainMenu();
            System.out.print("Enter choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    manageVehicles();
                    break;

                case 2:
                    manageCustomers();
                    break;

                case 3:
                    manageBookings();
                    break;

                case 4:
                    manageInvoices();
                    break;

                case 5:
                    manageCategories();
                    break;
                
                case 0:
                    System.out.println("Exiting EcoRide System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }


        } while (choice != 0);
    }

    // Main MENU
    private void showMainMenu() {
        System.out.println("\n|===========================================|");
        System.out.println("|======== EcoRide Car Rental System ========|");
        System.out.println("|===========================================|");
        System.out.println("|                                           |");
        System.out.println("|  1. Vehicle Operations                    |");
        System.out.println("|  2. Customer Operations                   |");
        System.out.println("|  3. Booking Operations                    |");
        System.out.println("|  4. Invoice Operations                    |");
        System.out.println("|  5. Category Operations                   |");
        System.out.println("|  0. Exit                                  |");
        System.out.println("|                                           |");
        System.out.println("|===========================================|");
        System.out.println("|******* Created by : Himasha Semini *******|");
        System.out.println("|===========================================|");
    }

    // VEHICLE MANAGEMENT MENU
    private void manageVehicles() {
        int choice;
        do {
            System.out.println("\n|===========================================|");
            System.out.println("|             Vehicle Operations            |");
            System.out.println("|-------------------------------------------|");
            System.out.println("|                                           |");
            System.out.println("|  1. Add Vehicle                           |");
            System.out.println("|  2. View Vehicles                         |");
            System.out.println("|  3. Update Vehicle Status                 |");
            System.out.println("|  4. Remove Vehicle                        |");
            System.out.println("|  0. Back to Main Menu                     |");
            System.out.println("|                                           |");
            System.out.println("|===========================================|");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    vehicleOps.addCar();
                    break;

                case 2:
                    vehicleOps.viewCars();
                    break;

                case 3:
                    vehicleOps.updateCarStatus();
                    break;

                case 4:
                    vehicleOps.removeCar();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        } while (choice != 0);
    }

    // CUSTOMER MANAGEMENT MENU
    private void manageCustomers() {
        int choice;
        do {
            System.out.println("\n|===========================================|");
            System.out.println("|            Customer Operations            |");
            System.out.println("|-------------------------------------------|");
            System.out.println("|                                           |");
            System.out.println("|  1. Register Customer                     |");
            System.out.println("|  2. Update Customer                       |");
            System.out.println("|  3. Delete Customer                       |");
            System.out.println("|  4. View Customers                        |");
            System.out.println("|  5. Search Customer                       |");
            System.out.println("|  0. Back to Main Menu                     |");
            System.out.println("|                                           |");
            System.out.println("|===========================================|");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customerOps.registerCustomer();
                    break;
                
                case 2:
                    customerOps.updateCustomer();
                    break;
                
                case 3:
                    customerOps.deleteCustomer();
                    break;

                case 4:
                    customerOps.viewCustomers();
                    break;
                
                case 5:
                    customerOps.searchCustomer();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        } while (choice != 0);
    }

    // BOOKING MANAGEMENT MENU
    private void manageBookings() {
        int choice;
        do {
            System.out.println("\n|===========================================|");
            System.out.println("|             Booking Operations            |");
            System.out.println("|-------------------------------------------|");
            System.out.println("|                                           |");
            System.out.println("|  1. Create Booking                        |");
            System.out.println("|  2. View Bookings                         |");
            System.out.println("|  3. Update Booking (within 2 days)        |");
            System.out.println("|  4. Cancel Booking (within 2 days)        |");
            System.out.println("|  0. Back to Main Menu                     |");
            System.out.println("|                                           |");
            System.out.println("|===========================================|");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    bookingOps.createBooking();
                    break;

                case 2:
                    bookingOps.viewBookings();
                    break;

                case 3:
                    bookingOps.updateBooking();
                    break;

                case 4:
                    bookingOps.cancelBooking();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        } while (choice != 0);
    }

    // INVOICE MANAGEMENT MENU
    private void manageInvoices() {
        int choice;
        do {
            System.out.println("\n|===========================================|");
            System.out.println("|             Invoice Operations            |");
            System.out.println("|-------------------------------------------|");
            System.out.println("|                                           |");
            System.out.println("|  1. Generate Invoice                      |");
            System.out.println("|  2. View Invoices                         |");
            System.out.println("|  0. Back to Main Menu                     |");
            System.out.println("|                                           |");
            System.out.println("|===========================================|");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    invoiceOps.generateInvoice();
                    break;

                case 2:
                    invoiceOps.viewInvoices();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        } while (choice != 0);
    }

    // CATEGORY MANAGEMENT MENU
    private void manageCategories() {
    int choice;
    do {
        System.out.println("\n|===========================================|");
        System.out.println("|            Category Operations            |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|                                           |");
        System.out.println("|  1. View Categories                       |");
        System.out.println("|  2. Update Category                       |");
        System.out.println("|  0. Back to Main Menu                     |");
        System.out.println("|                                           |");
        System.out.println("|===========================================|");
        System.out.print("Enter choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                categoryOps.viewCategories();
                break;

            case 2:
                categoryOps.updateCategory();
                break;

            case 0:
                System.out.println("Returning to main menu...");
                break;

            default:
                System.out.println("Invalid choice!");
                break;
        }

    } while (choice != 0);
}


}
