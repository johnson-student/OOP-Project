import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DeliverySystem system = new DeliverySystem("CADT Cafe", "013261425","Phnom Penh");

        int choice;

        do {

            if (!system.isStaffLoggedIn()) {

                printMainMenu();

                System.out.print("Choose: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: {
                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        system.staffLogin(username, password);
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 2: {
                        system.printMenuItems();
                        break;
                    }

                    case 0: {
                        System.out.println("Goodbye!");
                        break;
                    }

                    default:
                        System.out.println("Invalid choice.");
                }

            } else {

                printStaffMenu(system);

                System.out.print("Choose: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: { // Create Staff
                        System.out.print("Staff ID: ");
                        String staffId = sc.nextLine();

                        System.out.print("Full Name: ");
                        String fullName = sc.nextLine();

                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Position: ");
                        String position = sc.nextLine();

                        system.createStaff(staffId, fullName, phone, username, password, position);
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 2: { // Create Customer
                        System.out.print("Customer ID: ");
                        String customerId = sc.nextLine();

                        System.out.print("Full Name: ");
                        String fullName = sc.nextLine();

                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Initial Balance: ");
                        double balance = sc.nextDouble();
                        sc.nextLine();

                        system.createCustomer(customerId, fullName, phone, password, balance);
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 3: { // Create Menu Item
                        System.out.print("Item ID: ");
                        String itemId = sc.nextLine();

                        System.out.print("Item Name: ");
                        String name = sc.nextLine();

                        System.out.print("Category: ");
                        String category = sc.nextLine();

                        System.out.print("Size (S/M/L/None): ");
                        String size = sc.nextLine();

                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Available? (1=Yes, 0=No): ");
                        int a = sc.nextInt();
                        sc.nextLine();

                        boolean available = (a == 1);

                        system.createMenuItem(itemId, name, category, size, price, available);
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 4: { // Set Menu Item Availability
                        System.out.print("Item ID: ");
                        String itemId = sc.nextLine();

                        System.out.print("Available? (1=Yes, 0=No): ");
                        int a = sc.nextInt();
                        sc.nextLine();

                        boolean available = (a == 1);

                        system.setMenuItemAvailability(itemId, available);
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 5: { // Create Order
                        System.out.print("Customer phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Menu item ID: ");
                        String itemId = sc.nextLine();

                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        system.createOrder(phone, itemId, qty);
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 6: { // List Customers
                        system.printCustomers();
                        break;
                    }

                    case 7: { // List Menu Items
                        system.printMenuItems();
                        break;
                    }

                    case 8: { // List Orders
                        system.printOrders();
                        break;
                    }

                    case 9: { // Logout
                        system.staffLogout();
                        System.out.println(system.getLastMessage());
                        break;
                    }

                    case 0: {
                        System.out.println("Goodbye!");
                        break;
                    }

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } while (choice != 0);

        sc.close();
    }

    // ===== Menu printing in Main (easy to customize later) =====
    private static void printMainMenu() {
        System.out.println("\n=== MAIN MENU (Not Logged In) ===");
        System.out.println("1) Staff Login");
        System.out.println("2) View Menu Items");
        System.out.println("0) Exit");
    }

    private static void printStaffMenu(DeliverySystem system) {
        System.out.println("\n=== STAFF MENU (Logged In) ===");
        System.out.println("Logged in staff: " + system.getLoggedInStaff());
        System.out.println("1) Create Staff");
        System.out.println("2) Create Customer");
        System.out.println("3) Create Menu Item");
        System.out.println("4) Set Menu Item Availability");
        System.out.println("5) Create Order");
        System.out.println("6) List Customers");
        System.out.println("7) List Menu Items");
        System.out.println("8) List Orders");
        System.out.println("9) Logout");
        System.out.println("0) Exit");
    }
}