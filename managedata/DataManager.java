package managedata;
import models.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final String DATA_PATH = "data/";

    // SAVE METHODS

    public void saveCars(List<Car> cars) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH + "cars.txt"))) {
            for (Car c : cars) {
                writer.printf("%s,%s,%s,%.2f,%s%n",
                        c.getCarId(),
                        c.getModel(),
                        c.getCategory().getName(),
                        c.getDailyRentalPrice(),
                        c.getAvailabilityStatus());
            }
        } catch (IOException e) {
            System.out.println("Error saving cars: " + e.getMessage());
        }
    }

    public void saveCustomers(List<Customer> customers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH + "customers.txt"))) {
            for (Customer c : customers) {
                writer.printf("%s,%s,%s,%s,%s%n",
                        c.getCustomerId(),
                        c.getName(),
                        c.getNicOrPassport(),
                        c.getContactNumber(),
                        c.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    public void saveBookings(List<Booking> bookings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH + "bookings.txt"))) {
            for (Booking b : bookings) {
                writer.printf("%s,%s,%s,%s,%d,%.2f%n",
                        b.getBookingId(),
                        b.getCustomer().getCustomerId(),
                        b.getCar().getCarId(),
                        b.getBookingDate(),
                        b.getRentalDays(),
                        b.getEstimatedKm(),
                        b.getActualKm() 
                        );
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    public void saveCategories(List<Category> categories) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH + "categories.txt"))) {
            for (Category c : categories) {
                writer.printf("%s,%.2f,%d,%.2f,%.2f%n",
                        c.getName(),
                        c.getDailyRentalFee(),
                        c.getFreeKmPerDay(),
                        c.getExtraKmCharge(),
                        c.getTaxRate());
            }
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    public void saveInvoices(List<Invoice> invoices) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH + "invoices.txt"))) {
            for (Invoice inv : invoices) {
                writer.printf("%s,%s,%s,%.2f%n",
                        inv.getInvoiceId(),
                        inv.getBooking().getBookingId(),
                        inv.getIssueDate(),
                        inv.getFinalAmount());
            }
        } catch (IOException e) {
            System.out.println("Error saving invoices: " + e.getMessage());
        }
    }

    // LOAD METHODS 

    public List<Car> loadCars() {
        List<Car> cars = new ArrayList<>();
        File file = new File(DATA_PATH + "cars.txt");
        if (!file.exists()) return cars;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    Category category = new Category(data[2], Double.parseDouble(data[3]), 0, 0, 0);
                    cars.add(new Car(data[0], data[1], category, Double.parseDouble(data[3]), data[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading cars: " + e.getMessage());
        }
        return cars;
    }

    public List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(DATA_PATH + "customers.txt");
        if (!file.exists()) return customers;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    customers.add(new Customer(data[0], data[1], data[2], data[3], data[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
        return customers;
    }

    public List<Booking> loadBookings(List<Car> cars, List<Customer> customers) {
    List<Booking> bookings = new ArrayList<>();
    File file = new File(DATA_PATH + "bookings.txt");
    if (!file.exists()) return bookings;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7) {

                String id = data[0].trim();
                String customerId = data[1].trim();
                String carId = data[2].trim();

                Customer customer = findCustomerById(customers, customerId);
                Car car = findCarById(cars, carId);

                if (customer != null && car != null) {
                    Booking b = new Booking(
                            id,
                            customer,
                            car,
                            LocalDate.parse(data[3].trim()),
                            Integer.parseInt(data[4].trim()),
                            Double.parseDouble(data[5].trim()),
                            Double.parseDouble(data[6].trim())
                    );
                    bookings.add(b);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return bookings;
}

    
    public List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
        File file = new File(DATA_PATH + "categories.txt");
        if (!file.exists()) return categories;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    categories.add(new Category(
                            data[0],
                            Double.parseDouble(data[1]),
                            Integer.parseInt(data[2]),
                            Double.parseDouble(data[3]),
                            Double.parseDouble(data[4])
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading categories: " + e.getMessage());
        }
        return categories;
    }

    public List<Invoice> loadInvoices(List<Booking> bookings) {
            List<Invoice> invoices = new ArrayList<>();
            File file = new File(DATA_PATH + "invoices.txt");
            if (!file.exists()) return invoices;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 4) {
                        Booking booking = findBookingById(bookings, data[1].trim());
                        if (booking != null) {
                            Invoice inv = new Invoice(data[0].trim(), booking);
                            inv.setIssueDate(LocalDate.parse(data[2].trim()));
                            inv.setFinalAmount(Double.parseDouble(data[3].trim()));
                            invoices.add(inv);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading invoices: " + e.getMessage());
            }
            return invoices;
    }

    // HELPER METHODS

    // Find customer by ID from list
    private Customer findCustomerById(List<Customer> customers, String customerId) {
        for (Customer c : customers) {
            if (c.getCustomerId().equalsIgnoreCase(customerId.trim())) {
                return c;
            }
        }
        return null;
    }

    // Find car by ID from list
    private Car findCarById(List<Car> cars, String carId) {
        for (Car c : cars) {
            if (c.getCarId().equalsIgnoreCase(carId.trim())) {
                return c;
            }
        }
        return null;
    }

    private Booking findBookingById(List<Booking> bookings, String id) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(id.trim())) return b;
        }
        return null;
    }

    
}
