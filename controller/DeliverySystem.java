package controller;

import java.io.Console;
import java.io.IOException;
// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import other.Address;
import other.DeliveryRequest;
import other.Parcel;
import user.Clerk;
import user.Courier;
import user.Manager;
import user.Staff;
import user.StaffFilter;
import user.User;
import other.Pricing;

public class DeliverySystem {

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_USER = "CREATE_CUSTOMER";
    public static final String UPDATE_DELI_PRICE = "UPDATE_DELI_PRICE";
    public static final String CREATE_REQUEST = "CREATE_REQUEST";
    public static final String VIEW_ALL_DELIVERY = "VIEW_ALL_DELIVERY";
    public static final String VIEW_REQUESTS = "VIEW_REQUESTS";
    public static final String UPDATE_DELIVERY_STATUS = "UPDATE_DELIVERY_STATUS";
    public static final String VIEW_DELIVERY_PRICE = "VIEW_DELIVERY_PRICE";

    private String systemName;
    private String location;
    private static Pricing pricing;
    private ArrayList<Staff> staffs;
    private ArrayList<User> users;
    private ArrayList<Parcel> parcels;
    private ArrayList<DeliveryRequest> requests;
    private String telegram;

    private Staff loggedInStaff;
    private String lastMessage;

    DeliverySystem(String systemName, String telegram, String location) {
        staffs = new ArrayList<>();
        users = new ArrayList<>();
        requests = new ArrayList<>();
        parcels = new ArrayList<>();
        this.location = location;
        this.systemName = systemName;
        this.telegram = telegram;
        pricing = new Pricing(2.00, 1.2, 1, 0.5);
        // Default admin (so system can start)
        seedDefaultAdmin();

    }

    public void addStaff(Staff staff) {
        if (staff == null) {
            setLastMessage("Staff cannot be null");
            return;
        }
        staffs.add(staff);
    }

    public void addUser(User user) {
        if (user == null) {
            setLastMessage("User cannot be null");
            return;
        }
        users.add(user);
    }

    public void addParcel(Parcel parcel) {
        if (parcel == null) {
            setLastMessage("Parcel cannot be null");
            return;
        }
        parcels.add(parcel);
    }

    // Getters
    public int getParcelCount() {
        return parcels.size();
    }

    public boolean isStaffLoggedIn() {
        return loggedInStaff != null;
    }

    public Staff getLoggedInStaff() {
        return loggedInStaff;
    }

    public String getName() {
        return systemName;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Staff> getstaffs() {
        return staffs;
    }

    public String getTele() {
        return telegram;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    private void setLastMessage(String msg) {
        lastMessage = msg;
    }

    // default admin
    private void seedDefaultAdmin() {
        Manager admin = new Manager("Alice Smith", "admin", "054154444", "Admin@12345", 5.0, 500);
        staffs.add(admin);
        Courier C1 = new Courier("Bob Johnson", "courier1", "054154555", "Courier@12345", 4.5, 400);
        staffs.add(C1);
        // staffs.add(new Courier());
        // staffs.add(new ManagerStaff());
        // for(Staff staff : staffs){
        // System.out.println(staff.can("CREATE_ORDER"));
        // }
    }

    // require permission
    private boolean requirePermission(String action) {
        if (loggedInStaff == null) {
            setLastMessage("Please login first");
            return false;
        }

        if (!loggedInStaff.can(action)) {
            setLastMessage("Permission denied");
            return false;
        }

        return true;

    }

    // Login check
    private boolean requireStaffLogin() {
        if (loggedInStaff == null) {
            setLastMessage("Action denied: staff must login first.");
            return false;
        }

        if (!loggedInStaff.isActive()) {
            loggedInStaff = null;
            setLastMessage("Action denied: staff is inactive (auto logout).");
            return false;
        }

        return true;
    }

    // login / logout
    public void staffLogin(String username, String password) {

        if (isBlank(username) || password == null) {
            setLastMessage("Login failed: missing username/password.");
            return;
        }

        for (int i = 0; i < staffs.size(); i++) {
            Staff s = staffs.get(i);

            if (s.getUsername().equalsIgnoreCase(username.trim())) {

                if (!s.isActive()) {
                    setLastMessage("Login failed: staff is inactive.");
                    return;
                }

                if (!s.checkPassword(password)) {
                    setLastMessage("Login failed: wrong password.");
                    return;
                }

                loggedInStaff = s;
                setLastMessage("Login success. Welcome " + s.getFullname() + "!");
                return;
            }
        }

        setLastMessage("Login failed: username not found.");
    }

    public void staffLogout() {
        loggedInStaff = null;
        setLastMessage("Logged out successfully.");
    }

    // create staff
    public void createStaff(String fullName, String phone,
            String username, String password, String position) {

        if (!requireStaffLogin() || !requirePermission(CREATE_STAFF))
            return;

        if (isBlank(username)) {
            setLastMessage("Cannot create staff: username is empty.");
            return;
        }

        // duplicate username check
        for (int i = 0; i < staffs.size(); i++) {
            if (staffs.get(i).getUsername().equalsIgnoreCase(username.trim())) {
                setLastMessage("Cannot create staff: username already exists.");
                return;
            }
        }

        if (position.equals("Manager")) {
            staffs.add(new Manager(fullName, username, phone, password, 0.0, 5000));
            setLastMessage("Manager created successfully.");
        } else if (position.equals("Courier")) {
            staffs.add(new Courier(fullName, username, phone, password, 0.0, 400));
            setLastMessage("Courier created successfully.");
        } else if (position.equals("Clerk")) {
            staffs.add(new Clerk(fullName, username, phone, password, 0.0, 300));
            setLastMessage("Clerk created successfully.");
        }
    }

    public void createUser(String userName, String phone,
            String password, boolean isMember) {

        if (!requireStaffLogin())
            return;

        if (isBlank(phone)) {
            setLastMessage("Cannot create customer: phone is empty.");
            return;
        }

        // duplicate check
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPhone().equals(phone.trim())) {
                setLastMessage("Cannot create customer: phone already exists.");
                return;
            }
        }
        users.add(new User(userName, phone, password, isMember));
        setLastMessage("Customer created successfully.");
    }

    public Parcel createParcel(String type, double weight, double price, String senderID) {
        return new Parcel(type, weight, price, senderID);
    }

    // calculate fee
    public static double calculateFee(Parcel parcel) {
        if (parcel == null) {
            return 0.0;
        }

        if (parcel.getType().equals("liquid") || parcel.getType().equals("glass")) {
            return parcel.getWeight() * pricing.getVulnerable() + pricing.getBaseFee();
        } else if (parcel.getType().equals("device")) {
            return parcel.getWeight() * pricing.getDevice() + pricing.getBaseFee();
        }

        return parcel.getWeight() * pricing.getGeneral() + pricing.getBaseFee();
    }

    // Update price
    public void modifyPrice(double base, double vul, double device, double general) {
        if (pricing == null) {
            pricing = new Pricing(base, vul, device, general);
        } else {
            pricing.setBaseFee(base);
            pricing.setVulnerable(vul);
            pricing.setDevice(device);
            pricing.setGeneral(general);
        }
        setLastMessage("Pricing updated successfully.");
    }

    public void viewPrice() {
        System.out.println(pricing.toString());
    }

    // courier view sender info
    public void viewUserRequest() {

        if (!requireStaffLogin())
            return;

        if (requests.isEmpty()) {
            setLastMessage("No request Today");
            return;
        }

        // Only courier allowed
        if (!(loggedInStaff instanceof Courier)) {
            setLastMessage("Only courier can view user requests.");
            return;
        }

        boolean found = false;
        Courier courier = (Courier) loggedInStaff;

        for (DeliveryRequest r : requests) {
            if (r.getCourier().equals(courier)) {

                System.out.println(r.toString());
                found = true;
            }
        }

        if (!found) {
            setLastMessage("No request");
        } else {
            setLastMessage("On Duty");
        }
    }

    // view all delivery
    public void viewALLDelivery() {

        if (!requireStaffLogin())
            return;

        if (requests.isEmpty()) {
            System.out.println("No delivery requests found.");
            return;
        }

        for (DeliveryRequest r : requests) {

            String courierName = r.getCourier().getFullname();
            String courierID = r.getCourier().getStaffId(); // or ID if you have

            String senderName = r.getUser().getName();
            String senderID = r.getUser().getUserID();

            System.out.println(
                    "Status" + "(" + r.getCourier().isAvailable() + ")" + courierName + " (" + "ID" + courierID
                            + ") -> " + senderName + " (" + "ID" + senderID + ")");
        }
    }

    // find User by phone
    public User findUserByPhone(String phone) {
        if (isBlank(phone)) {
            setLastMessage("Phone number cannot be empty.");
            return null;
        }

        return users.stream()
                .filter(u -> phone != null && u.getPhone().equals(phone))
                .findFirst()
                .orElse(null);

    }

    // Get all active staff
    public ArrayList<Staff> getActiveStaff() {
        return filterStaff(activeFilter);
    }

    // Get all top-rated staff
    public ArrayList<Staff> getTopRatedStaff() {
        return filterStaff(topRatedFilter);
    }

    // Get all available couriers
    public ArrayList<Courier> getAllAvailableCouriers() {
        ArrayList<Courier> result = new ArrayList<>();
        for (Staff s : filterStaff(availableCourierFilter)) {
            result.add((Courier) s);
        }
        return result;
    }

    // Lambda that checks if staff is active
    private StaffFilter activeFilter = s -> s.isActive();

    // Lambda variable to filter available couriers
    private StaffFilter availableCourierFilter = s -> s instanceof Courier && ((Courier) s).isAvailable();

    // Lambda that checks if staff has rating > 4.5
    private StaffFilter topRatedFilter = s -> s.getRating() > 4.5;

    public ArrayList<Staff> filterStaff(StaffFilter filter) {
        ArrayList<Staff> result = new ArrayList<>();

        for (Staff s : staffs) {
            if (filter.apply(s)) { // THIS is where lambda runs
                result.add(s);
            }
        }

        return result;
    }

    // Set Status Courier
    public void updateStatus(boolean aviable) {
        if (!(requireStaffLogin()))
            return;

        Courier courier = (Courier) loggedInStaff;
        courier.setStatus(aviable);

        setLastMessage("Update status to: " + courier.isAvailable());
    }

    // createrequest
    public DeliveryRequest createRequest(
            User sender,
            User receiver,
            Parcel parcel) {
        Courier courier = getAllAvailableCouriers().stream().findFirst().orElse(null);

        addParcel(parcel);

        if (courier == null) {
            System.out.println("No courier available.");
            return null;
        }
        double fee = calculateFee(parcel);
        DeliveryRequest request = new DeliveryRequest(sender, receiver, parcel, courier, fee);
        requests.add(request);

        courier.setStatus(false);

        return request;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "DeliverySystem [systemName=" + systemName + ", couriers=" + Arrays.toString(staffs.toArray())
                + ", users=" + Arrays.toString(users.toArray()) + ", parcels=" + Arrays.toString(parcels.toArray())
                + ", StaffCount=" + staffs.size() + "]";
    }

    // Run function
    private ArrayList<String> menuActions = new ArrayList<>();
    private int opt = 1;

    public void runCode() {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        do {
            try {
                if (!isStaffLoggedIn()) {

                    printMainMenu();

                    try {
                        System.out.print("Choose: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input! Enter a number.");
                        sc.nextLine();
                        continue;
                    }

                    switch (choice) {
                        case 1: {
                            try {

                                System.out.print("Username: ");
                                String username = sc.nextLine();

                                Console console = System.console();

                                if (console == null) {
                                    System.out.println("No console available (run in terminal)");
                                } else {
                                    char[] password = console.readPassword("Password: ");
                                    String passwordStr = new String(password);

                                    staffLogin(username, passwordStr);
                                }
                                System.out.print(lastMessage);
                                break;
                            } catch (Exception e) {

                            }
                        }

                        case 0:
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }

                } else {

                    printStaffMenu();

                    try {
                        System.out.print("Choose: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input! Enter a number.");
                        sc.nextLine();
                        continue;
                    }

                    if (choice == 0) {
                        System.out.println("Goodbye!");
                        break;
                    }

                    if (choice < 1 || choice > menuActions.size()) {
                        System.out.println("Invalid choice.");
                        continue;
                    }

                    String action = menuActions.get(choice - 1);

                    switch (action) {

                        case CREATE_STAFF: {
                            try {
                                System.out.print("Full Name: ");
                                String fullName = sc.nextLine();

                                System.out.print("Phone: ");
                                String phone = sc.nextLine();

                                System.out.print("Username: ");
                                String username = sc.nextLine();

                                System.out.print("Password: ");
                                String password = sc.nextLine();

                                System.out.println("1) Manager\n2) Courier\n3) Clerk");
                                int role = sc.nextInt();
                                sc.nextLine();

                                String position;
                                if (role == 1)
                                    position = "Manager";
                                else if (role == 2)
                                    position = "Courier";
                                else if (role == 3)
                                    position = "Clerk";
                                else {
                                    System.out.println("Invalid position.");
                                    break;
                                }

                                createStaff(fullName, phone, username, password, position);
                                System.out.println(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Error creating staff.");
                                sc.nextLine();
                            }
                            break;
                        }

                        case CREATE_USER: {
                            String fullName;
                            String phone;
                            String password;
                            boolean isMember;

                            try {

                                // ✅ Name validation
                                while (true) {
                                    System.out.print("UserName: ");
                                    fullName = sc.nextLine();

                                    if (fullName.length() >= 8 && fullName.length() <= 16) {
                                        break;
                                    }
                                    System.out.println("Name must be 8-16 characters.");
                                }

                                // ✅ Phone validation
                                while (true) {
                                    System.out.print("Phone: ");
                                    phone = sc.nextLine();

                                    if(!phone.matches("\\d+")){
                                        System.out.println("Input most be a Number");
                                        continue;
                                    }

                                    if (!phone.matches("\\d{8,15}")) {
                                        System.out.println("Phone must be 8-15 digits.");
                                        continue;
                                    }

                                    // duplicate check
                                    if (findUserByPhone(phone) != null) {
                                        System.out.println("Phone already exists.");
                                        continue;
                                    }

                                    break;
                                }

                                while (true) {
                                    System.out.print("Password: ");
                                    password = sc.nextLine();

                                    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

                                    if (password.matches(regex)) {
                                        break;
                                    }

                                    System.out.println(
                                            "Password must have uppercase, lowercase, number, special char, min 8.");
                                }

                                while (true) {
                                    try {
                                        System.out.print("Is Member (true/false): ");
                                        isMember = sc.nextBoolean();
                                        sc.nextLine();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Enter only true or false.");
                                        sc.nextLine();
                                    }
                                }

                                createUser(fullName, phone, password, isMember);
                                System.out.println(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Unexpected error."+e);
                                sc.nextLine();
                            }

                            break;
                        }

                        case CREATE_REQUEST: {
                            try {
                                System.out.print("Phone: ");
                                String phone = sc.nextLine();
                                User sender = findUserByPhone(phone);

                                if (sender == null) {
                                    System.out.println("User not found.");
                                    break;
                                }

                                System.out.print("Type: ");
                                String type = sc.nextLine();

                                System.out.print("Weight: ");
                                double weight = sc.nextDouble();
                                sc.nextLine();

                                System.out.print("Price: ");
                                double price = sc.nextDouble();
                                sc.nextLine();

                                System.out.print("Receiver Phone: ");
                                String Phone = sc.nextLine();

                                System.out.print("Receiver Name: ");
                                String Name = sc.nextLine();

                                System.out.print("City: ");
                                String city = sc.nextLine();

                                System.out.print("District: ");
                                String district = sc.nextLine();

                                System.out.print("Street: ");
                                String street = sc.nextLine();

                                User receiver = new User(Name, Phone,
                                        new Address(city, district, street));

                                createRequest(sender, receiver,
                                        createParcel(type, weight, price, sender.getUserID()));

                                System.out.println(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Error creating request.");
                                sc.nextLine();
                            }
                            break;
                        }

                        case UPDATE_DELI_PRICE: {
                            try {
                                System.out.print("Base Fee: ");
                                double base = sc.nextDouble();

                                System.out.print("Vulnerable Price: ");
                                double vul = sc.nextDouble();

                                System.out.print("Device Price: ");
                                double device = sc.nextDouble();

                                System.out.print("General Price: ");
                                double general = sc.nextDouble();

                                modifyPrice(base, vul, device, general);
                                System.out.println(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Number only here!!");
                            }
                            break;
                        }

                        case UPDATE_DELIVERY_STATUS: {
                            try {

                                System.out.println("Choose 1: occuppied, 2:aviable");
                                int chose = sc.nextInt();

                                if (chose == 1) {
                                    updateStatus(false);
                                    System.out.println(getLastMessage());
                                } else if (chose == 2) {
                                    updateStatus(true);
                                    System.out.println(getLastMessage());
                                }
                            } catch (Exception e) {
                                System.out.println("Enter Number only");
                                sc.nextInt();
                            }

                            break;
                        }

                        case VIEW_ALL_DELIVERY:
                            viewALLDelivery();
                            break;

                        case VIEW_REQUESTS:
                            viewUserRequest();
                            System.out.println(getLastMessage());
                            break;

                        case VIEW_DELIVERY_PRICE:
                            viewPrice();
                            break;

                        case "LOGOUT":
                            staffLogout();
                            System.out.println(getLastMessage());
                            break;

                        default:
                            System.out.println("Invalid action.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
                sc.nextLine();
            }

        } while (choice != 0);

        sc.close();
    }

    // ===== Menu printing in Main (easy to customize later) =====
    private void printMainMenu() {
        System.out.println("\n=== MAIN MENU (Not Logged In) ===");
        System.out.println("1) Staff Login");
        System.out.println("0) Exit");
    }

    private void printStaffMenu() {
        menuActions.clear();
        opt = 1;
        System.out.println("\n=== STAFF MENU (Logged In) ===");
        System.out.println("Logged in staff: " + getLoggedInStaff());
        if (getLoggedInStaff().can("CREATE_STAFF")) {
            System.out.println(opt + ") Create Staff");
            menuActions.add(CREATE_STAFF);
            opt++;
        }
        if (getLoggedInStaff().can("CREATE_USER")) {
            System.out.println(opt + ") Create User");
            menuActions.add(CREATE_USER);
            opt++;
        }
        if (getLoggedInStaff().can("CREATE_REQUEST")) {
            System.out.println(opt + ")Create Request");
            menuActions.add(CREATE_REQUEST);
            opt++;
        }
        if (getLoggedInStaff().can("UPDATE_DELIVERY_STATUS")) {
            System.out.println(opt + ")UPDATE DELIVERY STATUS");
            menuActions.add(UPDATE_DELIVERY_STATUS);
            opt++;
        }
        if (getLoggedInStaff().can("UPDATE_DELI_PRICE")) {
            System.out.println(opt + ")UPDATE_DELI_PRICE");
            menuActions.add(UPDATE_DELI_PRICE);
            opt++;
        }
        if (getLoggedInStaff().can("VIEW_REQUESTS")) {
            System.out.println(opt + ")veiw User");
            menuActions.add(VIEW_REQUESTS);
            opt++;
        }
        if (getLoggedInStaff().can("VIEW_ALL_DELIVERY")) {
            System.out.println(opt + ")view All Delivery");
            menuActions.add(VIEW_ALL_DELIVERY);
            opt++;
        }

        System.out.println(opt + ") View Delivery Price");
        menuActions.add(VIEW_DELIVERY_PRICE);
        opt++;
        System.out.println(opt + ") Logout");
        menuActions.add("LOGOUT");

        System.out.println("0) Exit");

    }

}
