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
    public static final String CREATE_USER = "CREATE_USER";
    public static final String CREATE_REQUEST = "CREATE_REQUEST";
    public static final String UPDATE_DELI_PRICE = "UPDATE_DELI_PRICE";
    public static final String UPDATE_Courier_STATUS = "UPDATE_Courier_STATUS";
    public static final String UPDATE_REQUEST_STATUS = "UPDATE_REQUEST_STATUS";
    public static final String VIEW_ALL_DELIVERY = "VIEW_ALL_DELIVERY";
    public static final String VIEW_REQUESTS = "VIEW_REQUESTS";
    public static final String VIEW_ALL_DELIVERY_REQUEST = "VIEW_ALL_DELIVERY_REQUEST";
    public static final String VIEW_ALL_DELIVERY_HISTORY = "VIEW_ALL_DELIVERY_HISTORY";
    public static final String VIEW_DELIVERY_PRICE = "VIEW_DELIVERY_PRICE";
    public static final String VIEW_CURRENT_USER = "VIEW_CURRENT_USER";

    private String systemName;
    private Address location;
    private static Pricing pricing;
    private ArrayList<Staff> staffs;
    private ArrayList<User> users;
    private ArrayList<Parcel> parcels;
    private ArrayList<DeliveryRequest> requests;
    private String telegram;

    private Staff loggedInStaff;
    private String lastMessage;

    DeliverySystem(String systemName, String telegram, String city, String district, String Street) {
        staffs = new ArrayList<>();
        users = new ArrayList<>();
        requests = new ArrayList<>();
        parcels = new ArrayList<>();
        setLocation(city, district, Street);
        this.systemName = systemName;
        this.telegram = telegram;
        pricing = new Pricing(2.00, 1.2, 1, 0.5);
        // Default admin (so system can start)
        seedDefaultAdmin();

    }

    public void addStaff(Staff staff) {
        if (!(requireStaffLogin())) {
            System.out.print("Can't not add staff, Please Log IN");
            return;
        }
        if (staff == null) {
            setLastMessage("Staff cannot be null");
            return;
        }
        staffs.add(staff);
    }

    public void addUser(User user) {
        if (!(requireStaffLogin())) {
            System.out.print("Can't add user, Please Log IN");
            return;
        }
        if (user == null) {
            setLastMessage("User cannot be null");
            return;
        }
        users.add(user);
    }

    public void addParcel(Parcel parcel) {
        if (!(requireStaffLogin())) {
            System.out.print("can't not add parcel, Please Log IN");
            return;
        }
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

    public Address getLocation() {
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

    // setter
    private void setLastMessage(String msg) {
        lastMessage = msg;
    }

    private void setLocation(String city, String district, String street) {
        location = new Address(city, district, street);
    }

    // default admin
    private void seedDefaultAdmin() {
        Manager admin = new Manager("Alice Smith", "admin", "054154444", "Admin@12345", 5.0, 500);
        staffs.add(admin);
        Courier C1 = new Courier("Bob Johnson", "courier1", "054154555", "Courier@12345", 4.5, 400);
        staffs.add(C1);

        // anonymous class
        Courier C2 = new Courier("Charlie Brown", "courier2", "054154666", "Courier@12345", 4.8, 400) {
            @Override
            public boolean can(String action) {
                if (action.equals(CREATE_REQUEST)) {
                    return true;
                }
                return super.can(action);
            }
        };
        staffs.add(C2);
        // staffs.add(new Courier());
        // staffs.add(new ManagerStaff());
        // for(Staff staff : staffs){
        // System.out.println(staff.can("CREATE_ORDER"));
        // }
    }

    // require permission
    private boolean requirePermission(String action) {
        if (!requireStaffLogin())
            return false;

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
    public void staffLogin(String logMethod, String password) {

        if (isBlank(logMethod) || password == null) {
            setLastMessage("Login failed: missing logMethod/password.");
            return;
        }

        for (int i = 0; i < staffs.size(); i++) {
            Staff s = staffs.get(i);

            if (s.getUsername().equalsIgnoreCase(logMethod.trim()) || s.getPhone().equals(logMethod.trim())) {

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

        if (!requirePermission(CREATE_USER))
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
        if (!requirePermission(UPDATE_DELI_PRICE))
            return;

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

    // =================
    // View Current User
    // =================
    public void viewCurrentUser() {
        if (users == null || users.isEmpty()) {
            System.out.println("No User Created!!");
            return;
        }

        for (User u : users) {
            System.out.println(u);
        }
    }

    // ==============
    // Current record
    // ==============

    // courier view his request today
    public void viewRequest() {
        if (!requireStaffLogin())
            return;

        if (!(loggedInStaff instanceof Courier)) {
            System.out.println("Only courier can view requests.");
            return;
        }
        DeliveryRequest courierRequest = null;

        Courier courier = (Courier) loggedInStaff;

        for (DeliveryRequest r : requests) {
            if (r.getIsCompleted()) {
                continue;
            }
            if (r.getCourier() != null && r.getCourier().equals(courier)) {
                courierRequest = r;
                System.out.println(r.toString());
                break;
            }
        }

        if (courierRequest == null) {
            System.out.println("No request Today");
            return;
        }
        if (courier.isAvailable()) {
            courierRequest = null;
            System.out.println("No request");
            return;
        }
    }

    // view all current request detail
    public void viewAllDeliveryRequest() {
        if (!requireStaffLogin())
            return;

        if (requests.isEmpty()) {
            System.out.println("No request Today");
            return;
        }

        boolean hasRequest = false;
        int count = 0;
        for (DeliveryRequest r : requests) {
            if (r.getIsCompleted()) {
                continue;
            }
            if (r.getCourier() == null)
                continue;
            hasRequest = true;
            count++;
            String courierName = r.getCourier().getFullname();
            String courierID = r.getCourier().getStaffId(); // or ID if you have

            String senderName = r.getUser().getName();
            String senderID = r.getUser().getUserID();

            System.out.println(
                    count + ":" + "Status" + "(" + (r.getCourier().isAvailable() ? "Available" : "Ocuppied") + ") "
                            + courierName
                            + " (" + "ID" + (courierID == null ? "No ID" : courierID)
                            + ") -> " + senderName + " (" + "ID" + senderID + ")");

        }

        if (!hasRequest) {
            System.out.println("No more request");
            return;
        }
    }

    public void viewAllDeliveryDetail() {
        for (DeliveryRequest r : requests) {
            if (!(r.getIsCompleted())) {
                System.out.println(r.toString());
            }
        }
    }

    public void viewBreifDelivery() {
        for (DeliveryRequest r : requests) {
            if (!(r.getIsCompleted())) {
                r.printInfo();
            }
        }
    }

    // ==============
    // History record
    // ==============

    // courier view his request history
    public void viewHistoryByCourier() {

        if (!requireStaffLogin())
            return;

        if (requests.isEmpty()) {
            System.out.println("No request Today");
            return;
        }

        // Only courier allowed
        if (!(loggedInStaff instanceof Courier)) {
            System.out.println("Only courier can view user requests.");
            return;
        }

        boolean found = false;
        Courier courier = (Courier) loggedInStaff;

        for (DeliveryRequest r : requests) {
            if (r.getCourier() == null || !r.getIsCompleted()) {
                continue;
            }
            if (r.getCourier().equals(courier)) {

                System.out.println(r.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No request");
        } 
    }

    // view all delivery history detail and summery
    public void viewALLDeliveryHistory() {

        if (!requireStaffLogin())
            return;

        if (requests.isEmpty() || requests.stream().noneMatch(r -> r.getIsCompleted())) {
            System.out.println("No delivery requests found.");
            return;
        }
        int count = 0;
        for (DeliveryRequest r : requests) {
            if (!(r.getIsCompleted())) {
                continue;
            }
            count++;
            String courierName = r.getCourier().getFullname();
            String courierID = r.getCourier().getStaffId(); // or ID if you have

            String senderName = r.getUser().getName();
            String senderID = r.getUser().getUserID();

            System.out.println(
                    count + ":" + courierName + " (" + "ID" + (courierID == null ? "No ID" : courierID)
                            + ") -> " + senderName + " (" + "ID" + senderID + ")" + "{" + "Status: "
                            + (r.getIsCompleted() ? "Completed" : "Pending") + "}");
        }
    }

    public void viewFullDetailHistory() {
        for (DeliveryRequest r : requests) {
            if (!r.getIsCompleted()) {
                continue;
            }
            System.out.println(r.toString());
            System.out.println("Status : " + (r.getIsCompleted() ? "Completed" : "Pending"));
        }
    }

    public void viewBreifHistory() {
        for (DeliveryRequest r : requests) {
            if (!r.getIsCompleted()) {
                continue;
            }
            r.printInfo();
            System.out.println("Status : " + (r.getIsCompleted() ? "Completed" : "Pending"));
        }
    }

    // ==============
    // Update status
    // ==============

    // Set Status Courier
    public void updateStatus(boolean available) {
        if (!(requireStaffLogin()))
            return;
        if (!requirePermission(UPDATE_Courier_STATUS))
            return;

        if (!(getLoggedInStaff() instanceof Courier)) {
            System.out.println("You are not Courier");
            return;
        }

        Courier courier = (Courier) loggedInStaff;

        if (available && requests.stream()
                .anyMatch(r -> r.getCourier() != null && r.getCourier().equals(courier) && !r.getIsCompleted())) {
            setLastMessage("Please complete the current request before setting to available!!");
            return;
        }

        courier.setStatus(available);

        setLastMessage("Update status to: " + (courier.isAvailable() ? "Available" : "Occupied"));
    }

    public Staff findStaffById(String staffId) {
        for (Staff s : staffs) {
            if (s.getStaffId().equals(staffId)) {
                return s;
            }
        }
        return null;
    }

    // Update status by manager
    public void updateStatus(String staffId, boolean available) {
        if (!(requireStaffLogin())) {
            System.out.println("login first");
            return;
        }

        if (!(requirePermission(UPDATE_Courier_STATUS)))
            return;

        if (!(getLoggedInStaff() instanceof Manager)) {
            System.out.println("You are not Manager");
            return;
        }

        Staff staff = findStaffById(staffId);
        if (staff == null) {
            setLastMessage("Staff ID not found.");
            return;
        }
        if (!(staff instanceof Courier)) {
            setLastMessage("Staff is not a courier.");
            return;
        }
        if (available && requests.stream()
                .anyMatch(r -> r.getCourier() != null && r.getCourier().equals(staff) && !r.getIsCompleted())) {
            setLastMessage("Cannot set to available: courier is on duty!!");
            return;
        }
        Courier courier = (Courier) staff;
        courier.setStatus(available);

        setLastMessage("Update status to: " + (courier.isAvailable() ? "Available" : "Occupied"));
    }

    // Update Request Status by courier
    public void updateRequestStatus(boolean isCompleted) {
        if (!(requireStaffLogin()))
            return;

        if (!requirePermission(UPDATE_REQUEST_STATUS))
            return;

        if (!(loggedInStaff instanceof Courier)) {
            setLastMessage("Only courier allowed.");
            return;
        }
        if (requests == null || requests.isEmpty()) {
            setLastMessage("No request Today");
            return;
        }
        Courier courier = (Courier) loggedInStaff;
        boolean haveRequest = false;

        for (int i = requests.size() - 1; i >= 0; i--) {
            DeliveryRequest r = requests.get(i);
            if (r.getCourier() != null && r.getCourier().equals(courier) && !r.getIsCompleted()) {
                r.setIsCompleted(isCompleted);
                updateStatus(isCompleted);
                haveRequest = true;
                setLastMessage("Request " + r.getId() + " marked as " + (isCompleted ? "completed" : "pending"));
                break;
            }
        }
        if (!haveRequest) {
            setLastMessage("No More Request");
        }
    }

    // update by manager
    public void updateRequestStatus(String requestID, boolean isCompleted) {
        if (!(requireStaffLogin()))
            return;

        if (!requirePermission(UPDATE_REQUEST_STATUS))
            return;

        if (requests == null || requests.isEmpty()) {
            setLastMessage("No request");
            return;
        }

        boolean isMatch = false;

        for (int i = requests.size() - 1; i >= 0; i--) {
            DeliveryRequest r = requests.get(i);
            if (r.getId() != null && r.getId().equals(requestID)) {
                if(r.getIsCompleted()){
                    setLastMessage("Request "+r.getId()+" already set to complete");
                    return;
                }
                r.setIsCompleted(isCompleted);
                updateStatus(r.getCourier().getStaffId(), isCompleted);
                isMatch = true;
                setLastMessage("Request " + r.getId() + " marked as " + (isCompleted ? "completed" : "pending"));
                break;
            }
        }
        if (!isMatch) {
            setLastMessage("No match request ID");
        }
    }

    public void showAllStaff() {
        for (Staff s : staffs) {
            String position;
            if (s instanceof Courier) {
                position = "Courier";
            } else if (s instanceof Manager) {
                position = "Manager";
            } else if (s instanceof Clerk) {
                position = "Clerk";
            } else {
                position = "Unknown Position";
            }
            System.out.println(" Position: " + position + " === " + s.toString());
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

    // createrequest
    public DeliveryRequest createRequest(
            User sender,
            User receiver,
            Parcel parcel) {
        if (!requirePermission(CREATE_REQUEST))
            return null;

        Courier courier = getAllAvailableCouriers().stream().findFirst().orElse(null);

        if (courier == null) {
            System.out.println("No courier available.");
            return null;
        }
        addParcel(parcel);

        double fee = calculateFee(parcel);
        DeliveryRequest request = new DeliveryRequest(sender, receiver, parcel, courier, fee);
        requests.add(request);
        sender.increaseUseCount();

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

                                System.out.print("Username or Phone: ");
                                String logMethod = sc.nextLine();
                                if (logMethod.trim().isEmpty()) {
                                    System.out.print("Username or Phone can't be empty!!");
                                    break;
                                }

                                Console console = System.console();

                                if (console == null) {
                                    System.out.print("No console available (run in terminal)");
                                } else {
                                    char[] password = console.readPassword("Password: ");
                                    String passwordStr = new String(password);
                                    if (passwordStr.trim().isEmpty()) {
                                        System.out.print("Password can't be empty!!");
                                        break;
                                    }

                                    staffLogin(logMethod, passwordStr);
                                }
                                System.out.print(lastMessage);
                                break;
                            } catch (Exception e) {

                            }
                        }

                        case 0:
                            System.out.print("Goodbye!");
                            break;

                        default:
                            System.out.print("Invalid choice.");
                    }

                } else {

                    printStaffMenu();

                    try {
                        System.out.print("Choose: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                    } catch (Exception e) {
                        System.out.print("Invalid input! Enter a number.");
                        sc.nextLine();
                        continue;
                    }

                    if (choice == 0) {
                        System.out.print("Goodbye!");
                        break;
                    }

                    if (choice < 1 || choice > menuActions.size()) {
                        System.out.print("Invalid choice.");
                        continue;
                    }

                    String action = menuActions.get(choice - 1);

                    switch (action) {
                        case CREATE_STAFF: {
                            String fullName;
                            String phone;
                            String userName;
                            String password;
                            String position;

                            try {
                                while (true) {
                                    System.out.print("Full Name: ");
                                    fullName = sc.nextLine();
                                    if (fullName.length() >= 8 && fullName.length() <= 16) {
                                        break;
                                    }
                                    System.out.println("Name must be 8-16 characters.");

                                }

                                while (true) {
                                    System.out.print("Phone Number: ");
                                    phone = sc.nextLine();

                                    if (!phone.matches("\\d+")) {
                                        System.out.println("Input must be a Number");
                                        continue;
                                    }

                                    if (!phone.matches("\\d{8,15}")) {
                                        System.out.println("Phone must be 8-15 digits.");
                                        continue;
                                    }

                                    // duplicate check
                                    boolean existPhone = false;
                                    for (Staff s : staffs) {
                                        if (s.getPhone().equals(phone.trim())) {
                                            existPhone = true;
                                            break;
                                        }
                                    }

                                    if (existPhone) {
                                        System.out.println("Phone already exists.");
                                        continue;
                                    }
                                    break;
                                }

                                while (true) {
                                    System.out.print("UserName: ");
                                    userName = sc.nextLine();
                                    for (Staff s : staffs) {
                                        if (s.getUsername().equalsIgnoreCase(userName.trim())) {
                                            System.out.println("Username already exists.");
                                            userName = null;
                                            break;
                                        }
                                    }

                                    if (userName == null) {
                                        continue;
                                    }

                                    if (!(userName.length() >= 4 && userName.length() <= 16)) {
                                        System.out.println("Username must be 4-16 characters.");
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
                                        System.out.print("1) Manager\n2) Courier\n3) Clerk");
                                        int role = sc.nextInt();
                                        sc.nextLine();

                                        if (role == 1) {
                                            position = "Manager";
                                            break;
                                        } else if (role == 2) {
                                            position = "Courier";
                                            break;
                                        } else if (role == 3) {
                                            position = "Clerk";
                                            break;
                                        } else {
                                            System.out.println("Invalid position.");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Choose only Number!");
                                        sc.nextLine();
                                    }
                                }

                                createStaff(fullName, phone, userName, password, position);
                                System.out.print(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Error creating staff.");
                                e.printStackTrace();
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

                                while (true) {
                                    System.out.print("FullName: ");
                                    fullName = sc.nextLine();

                                    if (fullName.length() >= 8 && fullName.length() <= 16) {
                                        break;
                                    }
                                    System.out.println("Name must be 8-16 characters.");
                                }

                                while (true) {
                                    System.out.print("Phone number: ");
                                    phone = sc.nextLine();

                                    if (!phone.matches("\\d+")) {
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
                                        System.out.print("Is Member 1:true / 2:false): ");
                                        int chose = sc.nextInt();
                                        sc.nextLine();
                                        if (chose == 1) {
                                            isMember = true;
                                            break;
                                        } else if (chose == 2) {
                                            isMember = false;
                                            break;
                                        } else {
                                            System.out.println("invalid choice!");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Number only!");
                                        sc.nextLine();
                                    }
                                }

                                createUser(fullName, phone, password, isMember);
                                System.out.print(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Unexpected error." + e);
                                sc.nextLine();
                            }

                            break;
                        }

                        case CREATE_REQUEST: {
                            String sPhone;
                            User sender;
                            String type;
                            Double weight;
                            Double price;
                            String rPhone;
                            String Name;
                            String city;
                            String district;
                            String street;
                            Address location;
                            User receiver;

                            try {
                                if (getAllAvailableCouriers().isEmpty()) {
                                    System.out.print("No courier available. Can't create request.");
                                    break;
                                }

                                while (true) {

                                    System.out.print("Sender Phone number: ");
                                    sPhone = sc.nextLine();
                                    if(isBlank(sPhone)){
                                        System.out.println("Phone can't be empty");
                                        continue;
                                    }
                                    if (!sPhone.matches("\\d+")) {
                                        System.out.println("Input most be a Number");
                                        continue;
                                    }
                                    if (!sPhone.matches("\\d{8,15}")) {
                                        System.out.println("Phone must be 8-15 digits.");
                                        continue;
                                    }

                                    sender = findUserByPhone(sPhone);

                                    if (sender == null) {
                                        System.out.println("User not found.");
                                        sender = new User(sPhone);
                                        break;
                                    }
                                    break;
                                }

                                System.out.println("Receiver Info: ");
                                while (true) {

                                    System.out.print("Receiver Phone: ");
                                    rPhone = sc.nextLine();
                                       
                                    if(isBlank(rPhone)){
                                        System.out.println("Phone can't be empty");
                                        continue;
                                    }

                                    if (!rPhone.matches("\\d+")) {
                                        System.out.println("Input most be a Number");
                                        continue;
                                    }

                                    if (!rPhone.matches("\\d{8,15}")) {
                                        System.out.println("Phone must be 8-15 digits.");
                                        continue;
                                    }

                                    if (sender.getPhone().equals(rPhone)) {
                                        System.out.println("Sender and Reciever can be the same Phone number!!");
                                        continue;
                                    }

                                    break;
                                }

                                System.out.print("Receiver Name: ");
                                Name = sc.nextLine();

                                System.out.println("Destination Address: ");

                                while (true) {
                                    System.out.print("City: ");
                                    city = sc.nextLine();

                                    System.out.print("District: ");
                                    district = sc.nextLine();

                                    while (true) {
                                        System.out.print("Street number: ");
                                        street = sc.nextLine();

                                        if (!street.matches("\\d+")) {
                                            System.out.println("Input most be a Number");
                                            continue;
                                        }
                                        break;
                                    }

                                    location = new Address(city, district, street);
                                    if (location.equals(getLocation())) {
                                        System.out.println("Location can't be at the Branch");
                                        continue;
                                    }
                                    break;

                                }

                                while (true) {
                                    try {
                                        System.out.print(
                                                "choose Type: \n1): Liquid or Glass\n 2): Device\n 3): General");
                                        System.out.print("Choice: ");
                                        int chose = sc.nextInt();
                                        if (chose == 1) {
                                            type = "glass";
                                            break;
                                        } else if (chose == 2) {
                                            type = "device";
                                            break;
                                        } else if (chose == 3) {
                                            type = "general";
                                            break;
                                        } else {
                                            System.out.println("Choice Invalid! Choose 1-3 !!");
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Input Number only");
                                        sc.nextLine();
                                    }
                                }

                                while (true) {
                                    try {
                                        System.out.print("Weight: ");
                                        weight = sc.nextDouble();
                                        sc.nextLine();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Weight must be double!!");
                                        sc.nextLine();
                                    }
                                }
                                while (true) {
                                    try {
                                        System.out.print("Price: ");
                                        price = sc.nextDouble();
                                        sc.nextLine();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("Price must be double!!");
                                        sc.nextLine();
                                    }
                                }
                                receiver = new User(Name, rPhone, location);

                                createRequest(sender, receiver,
                                        createParcel(type, weight, price, sender.getUserID()));

                                System.out.print(getLastMessage());

                            } catch (Exception e) {
                                System.out.println("Unexpented Error!! Can't create request.");
                                sc.nextLine();
                            }
                            break;
                        }

                        case UPDATE_DELI_PRICE: {
                            while (true) {
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
                                    System.out.print(getLastMessage());

                                    break;
                                } catch (Exception e) {
                                    System.out.println("Number only here!!");
                                    sc.nextLine();
                                }
                            }
                            break;
                        }

                        case UPDATE_Courier_STATUS: {
                            if (loggedInStaff instanceof Courier) {
                                Courier courier = (Courier) loggedInStaff;
                                if (courier.isAvailable()) {
                                    updateStatus(false);
                                    System.out.print(getLastMessage());
                                    break;
                                } else {
                                    updateStatus(true);
                                    System.out.print(getLastMessage());
                                    break;
                                }
                            } else {
                                try {
                                    while (true) {
                                        System.out.print("Enter Courier ID: ");
                                        String id = sc.nextLine();
                                        if (id.trim().isEmpty()) {
                                            System.out.println("ID can't be empty!!");
                                            continue;
                                        }

                                        if (!id.matches("\\d++")) {
                                            System.out.println("Enter the Number only");
                                            continue;
                                        }
    
                                        Courier courier = (Courier) findStaffById(id);

                                        if (courier.isAvailable()) {
                                            updateStatus(id, false);
                                            System.out.println(getLastMessage());
                                            break;
                                        } else {
                                            updateStatus(id, true);
                                            System.out.println(getLastMessage());
                                            break;
                                        }

                                    }
                                } catch (Exception e) {
                                    // TODO: handle exception
                                    System.out.println("Error updating status.");
                                    sc.nextLine();
                                }
                            }
                            break;

                        }

                        case UPDATE_REQUEST_STATUS: {
                            if (requests == null || requests.isEmpty()) {
                                System.out.println("No request Today");
                                break;
                            }
                            if (getLoggedInStaff() instanceof Courier) {
                                updateRequestStatus(true);
                                System.out.println(getLastMessage());
                            } else {
                                try {
                                    while (true) {
                                        System.out.println("Updating Request Status");
                                        System.out.print("Request ID: ");
                                        String ID = sc.nextLine();
                                        if (ID.trim().isEmpty()) {
                                            System.out.println("ID can't be empty!!");
                                            continue;
                                        }

                                        if (!ID.matches("\\d++")) {
                                            System.out.println("Enter the Number only");
                                            continue;
                                        }

                                        updateRequestStatus(ID, true);
                                        System.out.println(getLastMessage());
                                        break;
                                    }

                                } catch (Exception e) {
                                    System.out.println("Error update");
                                    sc.nextLine();
                                }
                            }
                            break;
                        }

                        case VIEW_ALL_DELIVERY_HISTORY: {
                            while (true) {
                                try {
                                    System.out.println("\n\n");
                                    viewALLDeliveryHistory();
                                    System.out.println("1) Full detail  2)Summery   3)Exit");
                                    System.out.print("Choice: ");
                                    int chose = sc.nextInt();
                                    sc.nextLine();

                                    if (chose == 1) {
                                        viewFullDetailHistory();
                                    } else if (chose == 2) {
                                        viewBreifHistory();
                                    } else if (chose == 3) {
                                        break;
                                    } else {
                                        System.out.println("Invalid Choice!! Choose 1-3 ");
                                    }

                                } catch (Exception e) {
                                    System.out.println("Number only!!");
                                }

                            }
                            break;
                        }

                        case VIEW_ALL_DELIVERY: {
                            while (true) {
                                try {
                                    System.out.println("\n\n");
                                    viewAllDeliveryRequest();
                                    System.out.println("1) Full detail  2)Summery   3)Exit");
                                    System.out.println("Choice: ");
                                    int chose = sc.nextInt();
                                    sc.nextLine();

                                    if (chose == 1) {
                                        viewAllDeliveryDetail();
                                    } else if (chose == 2) {
                                        viewBreifDelivery();
                                    } else if (chose == 3) {
                                        break;
                                    } else {
                                        System.out.println("Invalid Choice!! Choose 1-3 ");
                                    }

                                } catch (Exception e) {
                                    System.out.println("Number only!!");
                                }
                            }
                            break;
                        }

                        case VIEW_CURRENT_USER:
                            viewCurrentUser();
                            break;

                        case VIEW_REQUESTS:
                            viewRequest();
                            break;

                        case VIEW_DELIVERY_PRICE:
                            viewPrice();
                            break;

                        case "historybycourier":
                            viewHistoryByCourier();
                            break;

                        case "SHOW_ALL_STAFF":
                            showAllStaff();
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
                e.printStackTrace();
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
        String position;
        if (getLoggedInStaff() instanceof Courier) {
            position = "Courier";
        } else if (getLoggedInStaff() instanceof Manager) {
            position = "Manager";
        } else if (getLoggedInStaff() instanceof Clerk) {
            position = "Clerk";
        } else {
            position = "Unknown Position";
        }

        System.out.println(
                "Logged by: " + getLoggedInStaff().getFullname() + "(" + position + ")" + " " + getLoggedInStaff());
        if (getLoggedInStaff().can("CREATE_STAFF")) {
            System.out.println(opt + ")Create Staff");
            menuActions.add(CREATE_STAFF);
            opt++;
        }
        if (getLoggedInStaff().can("CREATE_USER")) {
            System.out.println(opt + ")Create User");
            menuActions.add(CREATE_USER);
            opt++;
        }
        if (getLoggedInStaff().can("CREATE_REQUEST")) {
            System.out.println(opt + ")Create Request");
            menuActions.add(CREATE_REQUEST);
            opt++;
        }
        if (getLoggedInStaff().can("UPDATE_Courier_STATUS")) {
            System.out.println(
                    opt + (getLoggedInStaff() instanceof Courier ? ")Update My Status" : ")Update Courier Status"));
            menuActions.add(UPDATE_Courier_STATUS);
            opt++;
        }
        if (getLoggedInStaff().can("UPDATE_DELI_PRICE")) {
            System.out.println(opt + ")UPDATE_DELI_PRICE");
            menuActions.add(UPDATE_DELI_PRICE);
            opt++;
        }
        if (getLoggedInStaff().can("UPDATE_REQUEST_STATUS")) {
            System.out.println(opt + ")Update Request Status");
            menuActions.add(UPDATE_REQUEST_STATUS);
            opt++;
        }
        if (getLoggedInStaff().can("VIEW_REQUESTS") && getLoggedInStaff() instanceof Courier) {
            System.out.println(opt + ")View User Request Detial");
            menuActions.add(VIEW_REQUESTS);
            opt++;
        }
        if (getLoggedInStaff() instanceof Courier) {
            System.out.println(opt + ")View my history");
            menuActions.add("historybycourier");
            opt++;
        }
        
        if (getLoggedInStaff().can("VIEW_ALL_DELIVERY")) {
            System.out.println(opt + ")View All Delivery");
            menuActions.add(VIEW_ALL_DELIVERY);
            opt++;
        }
        if (getLoggedInStaff().can("VIEW_ALL_DELIVERY_HISTORY")) {
            System.out.println(opt + ")View All Delivery History");
            menuActions.add(VIEW_ALL_DELIVERY_HISTORY);
            opt++;
        }
        if (getLoggedInStaff().can("VIEW_CURRENT_USER")) {
            System.out.println(opt + ")View All User");
            menuActions.add(VIEW_CURRENT_USER);
            opt++;
        }

        System.out.println(opt + ")View Delivery Price");
        menuActions.add(VIEW_DELIVERY_PRICE);
        opt++;

        System.out.println(opt + ")Show All Staff");
        menuActions.add("SHOW_ALL_STAFF");
        opt++;

        System.out.println(opt + ")Logout");
        menuActions.add("LOGOUT");

        System.out.println("0) Exit");

    }

}
