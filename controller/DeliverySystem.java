package controller;
// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import other.DeliveryRequest;
import other.Parcel;
import user.Courier;
import user.Manager;
import user.Staff;
import user.User;

public class DeliverySystem {

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
    public static final String SET_PRICE_DELIVERY = "SET_PRICE_DELIVERY";
    public static final String CREATE_ORDER = "CREATE_ORDER";
    public static final String VIEW_CUSTOMERS = "VIEW_CUSTOMERS";
    public static final String VIEW_ORDERS = "VIEW_ORDERS";
    public static final String UPDATE_ORDER_STATUS = "UPDATE_ORDER_STATUS";

    private String systemName;
    private String location;
    private ArrayList<Staff> Staffs;
    private ArrayList<User> users;
    private ArrayList<Parcel> parcels;
    private ArrayList<DeliveryRequest> requests;
    private String telegram;
    private Staff loggedInStaff; 
    private String lastMessage;

    DeliverySystem(String systemName, String telegram, String location) {
        Staffs = new ArrayList<>();
        users = new ArrayList<>();
        requests = new ArrayList<>();
        parcels = new ArrayList<>(); 
        this.location =location;
        this.systemName = systemName;
        this.telegram = telegram;
        // Default admin (so system can start)
        seedDefaultAdmin();
    }

    public void addStaff(Staff staff) {
    if(staff == null) {
        setLastMessage("Staff cannot be null");
        return;
    }
        Staffs.add(staff);
    }

    public void addUser(User user) {
        if(user == null) {
            setLastMessage("User cannot be null");
            return;
        }
        users.add(user);
    }

    public void addParcel(Parcel parcel) {
    if(parcel == null) {
        setLastMessage("Parcel cannot be null");
        return;
    }
        parcels.add(parcel);
    }
    // Getters
    public int getParcelCount() {
        return parcels.size();
    }

    public boolean isStaffLoggedIn() { return loggedInStaff != null; }
    
    public Staff getLoggedInStaff() { return loggedInStaff; }

    public String getName(){
        return systemName;
    }

    public String getLocation(){
        return location;
    }

    public ArrayList<Staff> getStaffs() { return Staffs; }

    public String getTele(){
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
        Manager admin = new Manager("alice","admin", "054154444", "admin@12345", 5.0, 500);
        Staffs.add(admin);
        // staffs.add(new Courier());
        // staffs.add(new ManagerStaff());
        // for(Staff staff : staffs){
        //     System.out.println(staff.can("CREATE_ORDER"));
        // }
    }
    
// require permission
    private boolean requirePermission(String action)
    {
        if(loggedInStaff == null)
        {
            setLastMessage("Please login first");
            return false;
        }

        if(!loggedInStaff.can(action))
        {
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

        for (int i = 0; i < Staffs.size(); i++) {
            Staff s = Staffs.get(i);

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
    public void createStaff(String staffId, String fullName, String phone,
                            String username, String password, String position) {

        if (!requireStaffLogin() || !requirePermission(CREATE_STAFF)) return;

        if (isBlank(staffId) || isBlank(username)) {
            setLastMessage("Cannot create staff: staffId/username is empty.");
            return;
        }

        // duplicate username check
        for (int i = 0; i < Staffs.size(); i++) {
            if (Staffs.get(i).getUsername().equalsIgnoreCase(username.trim())) {
                setLastMessage("Cannot create staff: username already exists.");
                return;
            }
        }

        if(position.equals("Manager"))
        {
            Staffs.add(new Manager(fullName,username, phone, password, 0.0 , 5000));
            setLastMessage("Manager created successfully.");
        }else if(position.equals("Courier"))
        {   
            Staffs.add(new Courier(fullName,username, phone, password, 0.0 , 400));
            setLastMessage("Courier created successfully.");
        }
    }
    
    static double calculateFee(Parcel parcel) {
        if (parcel == null) {
            return 0.0;
        }

        double baseFee = 2.0;

        if (parcel.getType().equals("liquid") || parcel.getType().equals("glass")) {
            return parcel.getWeight() * 1.0 + baseFee;
        }

        return parcel.getWeight() * 0.5 + baseFee;
    }

    public void createUser(String fullName, String phone,
                               String password, boolean isMember) {

        if (!requireStaffLogin()) return;

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
        users.add(new User(fullName, phone, password, isMember));
        setLastMessage("Customer created successfully.");
    }

    // like getAvailableDriver()
    public Courier getAvailableCourier() {
        for (int i = 0; i < Staffs.size(); i++) {
            if (Staffs.get(i) instanceof Courier && ((Courier) Staffs.get(i)).isAvailable()) {
                return (Courier) Staffs.get(i);
            }
        }
        return null;
    }
    

    public DeliveryRequest createRequest(
            User sender,
            User receiver,
            Parcel parcel
    ) {
        Courier courier = getAvailableCourier();

        addParcel(parcel);

        if (courier == null) {
            System.out.println("No courier available.");
            return null;
        }
        double fee = calculateFee(parcel);
        DeliveryRequest request = new DeliveryRequest(sender, receiver, parcel, courier , fee);
        DeliveryRequest.requestCount++;
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
        return "DeliverySystem [systemName=" + systemName + ", couriers=" + Arrays.toString(Staffs.toArray()) + ", users=" + Arrays.toString(users.toArray()) + ", parcels=" + Arrays.toString(parcels.toArray())
                + ", StaffCount=" + Staffs.size() + "]";
    }
    
    public Parcel createParcel(String type, double weight, double price, String senderID){
        return new Parcel(type, weight, price, senderID);
    }
    
}
    
