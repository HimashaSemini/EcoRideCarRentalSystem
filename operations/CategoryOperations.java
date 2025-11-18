package operations;

import managedata.DataManager;
import models.Category;
import java.util.*;

public class CategoryOperations {
    private List<Category> categories;
    private DataManager dataManager;
    private Scanner scanner = new Scanner(System.in);

    public CategoryOperations(DataManager dataManager) {
        this.dataManager = dataManager;
        this.categories = dataManager.loadCategories();
    }

    // VIEW CATEGORIES
    public void viewCategories() {
        System.out.println("\n========== Available Categories ==========");
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            System.out.printf("%d. %s | Daily Fee: LKR %.2f | Free KM: %d | Extra: LKR %.2f/km | Tax: %.0f%%%n",
                    i + 1,
                    c.getName(),
                    c.getDailyRentalFee(),
                    c.getFreeKmPerDay(),
                    c.getExtraKmCharge(),
                    c.getTaxRate() * 100);
        }
    }

    // UPDATE CATEGORY
    public void updateCategory() {
        viewCategories();
        if (categories.isEmpty()) return;

        System.out.print("Enter category number to update: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > categories.size()) {
            System.out.println("Invalid choice!");
            return;
        }

        Category c = categories.get(choice - 1);

        System.out.println("Updating: " + c.getName());
        System.out.print("Enter new daily rental fee (current: " + c.getDailyRentalFee() + "): ");
        c.setDailyRentalFee(scanner.nextDouble());
        System.out.print("Enter new free km per day (current: " + c.getFreeKmPerDay() + "): ");
        c.setFreeKmPerDay(scanner.nextInt());
        System.out.print("Enter new extra km charge (current: " + c.getExtraKmCharge() + "): ");
        c.setExtraKmCharge(scanner.nextDouble());
        System.out.print("Enter new tax rate (current: " + c.getTaxRate() + "): ");
        c.setTaxRate(scanner.nextDouble());
        scanner.nextLine();

        dataManager.saveCategories(categories);
        System.out.println("Category updated successfully!!! ");
    }

    // GETTER
    public List<Category> getCategories() {
        return categories;
    }

    public Category getCategoryByName(String name) {
        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    //CHOOSE CATEGORY
    public Category chooseCategory() {
        viewCategories();
        System.out.print("Select a category number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > categories.size()) {
            System.out.println("Invalid category selection!");
            return null;
        }
        return categories.get(choice - 1);
    }
}
