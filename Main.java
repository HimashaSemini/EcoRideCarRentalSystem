import operations.Menus;

// MAIN CLASS TO START THE APPLICATION
public class Main {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("     Welcome to EcoRide Car Rental System     ");
        System.out.println("==============================================");
        
        Menus system = new Menus();
        system.startSystem();  
    }
}
