package operations;

import managedata.DataManager;
import models.Car;
import models.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VehicleOperations {
    private List<Car> cars;
    private DataManager dataManager;
    private CategoryOperations categoryOps;
    private Scanner scanner = new Scanner(System.in);

    public VehicleOperations(DataManager dataManager, CategoryOperations categoryOps) {
        this.dataManager = dataManager;
        this.categoryOps = categoryOps;
        this.cars = dataManager.loadCars();
    }

    // ADD NEW CAR 
    public void addCar() {
        System.out.println("\n========== Add New Vehicle ==========");

        System.out.print("Enter Car ID : ");
        String id = scanner.nextLine();

        System.out.print("Enter Model  : ");
        String model = scanner.nextLine();

        Category category = categoryOps.chooseCategory();
        if (category == null) return;

        System.out.printf("Selected Category: %s%n", category.getName());

        System.out.print("Enter Availability Status (Available / Reserved / Under Maintenance): ");
        String status = scanner.nextLine();

        Car newCar = new Car(id, model, category, category.getDailyRentalFee(), status);
        cars.add(newCar);

        dataManager.saveCars(cars);
        System.out.println("Vehicle added successfully!");
    }

    // VIEW ALL CARS
    public void viewCars() {
        System.out.println("\n========== Vehicle List ==========");
        if (cars.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }
        for (Car car : cars) {
            System.out.printf("ID: %s | Model: %s | Category: %s | Daily Fee: LKR %.2f | Status: %s%n",
                    car.getCarId(),
                    car.getModel(),
                    car.getCategory().getName(),
                    car.getDailyRentalPrice(),
                    car.getAvailabilityStatus());
        }
    }

    // UPDATE STATUS OF A CAR
    public void updateCarStatus() {
        System.out.println("\n========== Update Vehicle Status ==========");
        System.out.print("Enter Car ID: ");
        String id = scanner.nextLine();

        Car foundCar = findCarById(id);
        if (foundCar == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        System.out.print("Enter new status (Available / Reserved / Under Maintenance): ");
        String newStatus = scanner.nextLine();
        foundCar.setAvailabilityStatus(newStatus);

        dataManager.saveCars(cars);
        System.out.println("Vehicle status updated!");
    }

    // REMOVE CAR 
    public void removeCar() {
        System.out.println("\n========== Remove Vehicle ==========");
        System.out.print("Enter Car ID: ");
        String id = scanner.nextLine();

        Car carToRemove = findCarById(id);
        if (carToRemove == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        cars.remove(carToRemove);
        dataManager.saveCars(cars);
        System.out.println("Vehicle removed successfully!");
    }

    // FIND CAR BY ID
    private Car findCarById(String id) {
        for (Car c : cars) {
            if (c.getCarId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    // GETTER FOR OTHER CLASSES 
    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }
}
