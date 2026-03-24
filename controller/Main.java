package controller;
import java.util.Scanner;
import user.User;
import other.Address;
public class Main {

    public static void main(String[] args) {
    
        DeliverySystem system = new DeliverySystem("CADT Cafe", "013261425","Phnom Penh");
        system.runCode();

    //     int choice;

    //     do {

    //         if (!system.isStaffLoggedIn()) {

    //             printMainMenu();

    //             System.out.print("Choose: ");
    //             choice = sc.nextInt();
    //             sc.nextLine();

    //             switch (choice) {

    //                 case 1: {
    //                     System.out.print("Username: ");
    //                     String username = sc.nextLine();

    //                     System.out.print("Password: ");
    //                     String password = sc.nextLine();

    //                     system.staffLogin(username, password);
    //                     System.out.println(system.getLastMessage());
    //                     break;
    //                 }

    //                 case 0: {
    //                     System.out.println("Goodbye!");
    //                     break;
    //                 }

    //                 default:
    //                     System.out.println("Invalid choice.");
    //             }

    //         } else {

    //             printStaffMenu(system);

    //             System.out.print("Choose: ");
    //             choice = sc.nextInt();
    //             sc.nextLine();

    //             switch (choice) {

    //                 case 1: { // Create Staff
    //                     if(!system.getLoggedInStaff().can("CREATE_STAFF")) {
    //                         System.out.println("You do not have permission to create staff.");
    //                         break;
    //                     }
    //                     System.out.print("Full Name: ");
    //                     String fullName = sc.nextLine();

    //                     System.out.print("Phone: ");
    //                     String phone = sc.nextLine();

    //                     System.out.print("Username: ");
    //                     String username = sc.nextLine();

    //                     System.out.print("Password: ");
    //                     String password = sc.nextLine();

    //                     System.out.print("Choose Position: ");
    //                     System.out.println("1) Manager");
    //                     System.out.println("2) Courier");
    //                     System.out.println("3) Clerk");
    //                     int role = sc.nextInt();
    //                     sc.nextLine();
    //                     String position = "";
    //                     if(role == 1){
    //                         position = "Manager";
    //                     }else if(role == 2){
    //                         position = "Courier";
    //                     }else if(role == 3){
    //                         position = "Clerk";
    //                     }else{ 
    //                         System.out.println("Invalid position choice.");
    //                         break;
    //                     }

    //                     system.createStaff(fullName, phone, username, password, position);
    //                     System.out.println(system.getLastMessage());
    //                     break;
    //                 }

    //                 case 2: { // Create User
    //                     if(!system.getLoggedInStaff().can("CREATE_USER")) {
    //                         System.out.println("You do not have permission to create users.");
    //                         break;
    //                     }

    //                     System.out.print("Full Name: ");
    //                     String fullName = sc.nextLine();

    //                     System.out.print("Phone: ");
    //                     String phone = sc.nextLine();

    //                     System.out.print("Password: ");
    //                     String password = sc.nextLine();

    //                     System.out.print("Is Member? (true/false): ");
    //                     boolean isMember = sc.nextBoolean();
    //                     sc.nextLine();

    //                     system.createUser( fullName, phone, password, isMember);
    //                     System.out.println(system.getLastMessage());
    //                     break;
    //                 }
    //                 case 3: { // Create request
    //                     if(!system.getLoggedInStaff().can("CREATE_REQUEST")) {
    //                         System.out.println("You do not have permission to create requests.");
    //                         break;
    //                     }
    //                     System.out.print("Enter your phoneNumber: ");
    //                     String phone = sc.nextLine();
    //                     User sender =system.findUserByPhone(phone);
                        
    //                     System.out.print("Enter your Item info: ");
    //                     System.out.print("Enter your Item type: ");
    //                     String type = sc.nextLine();

    //                     System.out.print("Weight: ");
    //                     double weight = sc.nextInt();
    //                     sc.nextLine();
                        
    //                     System.out.print("price: ");
    //                     double price = sc.nextInt();
    //                     sc.nextLine();

    //                     System.out.print("Enter receiver info: ");
    //                     System.out.print("Enter receiver phoneNumber: ");
    //                     String receiverPhone = sc.nextLine();
    //                     System.out.print("Enter receiver name: ");
    //                     String receiverName = sc.nextLine();
    //                     System.out.print("Enter receiver address: ");
    //                     System.out.print("Enter receiver city: ");
    //                     String receiverCity = sc.nextLine();
    //                     System.out.print("Enter receiver district: "); 
    //                     String receiverDistrict = sc.nextLine();
    //                     System.out.print("Enter receiver street: ");
    //                     String receiverStreet = sc.nextLine();
    //                     User receiver = new User(receiverName, receiverPhone, new Address(receiverCity, receiverDistrict, receiverStreet));

                       
    //                     system.createRequest(sender, receiver, system.createParcel(type, weight, price , sender.getUserID()));
    //                     System.out.println(system.getLastMessage());
    //                     break;
    //                 }
    //                 case 4: { // Logout
    //                     system.staffLogout();
    //                     System.out.println(system.getLastMessage());
    //                     break;
    //                 }
    //                 case 0: {
    //                     System.out.println("Goodbye!");
    //                     break;
    //                 }

    //                 default:
    //                     System.out.println("Invalid choice.");
    //             }
    //         }

    //     } while (choice != 0);

    //     sc.close();
    // }

    // // ===== Menu printing in Main (easy to customize later) =====
    // private static void printMainMenu() {
    //     System.out.println("\n=== MAIN MENU (Not Logged In) ===");
    //     System.out.println("1) Staff Login");
    //     System.out.println("0) Exit");
    // }

    // private static void printStaffMenu(DeliverySystem system) {
    //     System.out.println("\n=== STAFF MENU (Logged In) ===");
    //     System.out.println("Logged in staff: " + system.getLoggedInStaff());
    //     System.out.println("1) Create Staff");
    //     System.out.println("2) Create User");
    //     System.out.println("3) Create Request");
    //     System.out.println("4) Logout");
    //     System.out.println("0) Exit");
    // }
    }
}