import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DeliverySystem {

    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String CREATE_CUSTOMER = "CREATE_CUSTOMER";
    public static final String SET_PRICE_DELIVERY = "SET_PRICE_DELIVERY";
    public static final String CREATE_ORDER = "CREATE_ORDER";
    public static final String VIEW_CUSTOMERS = "VIEW_CUSTOMERS";
    public static final String VIEW_ORDERS = "VIEW_ORDERS";
    public static final String UPDATE_ORDER_STATUS = "UPDATE_ORDER_STATUS";



    private String systemName;
    private ArrayList<Courier> couriers;
    private ArrayList<User> users;
    private ArrayList<Parcel> parcels;
    private String telegram;
    private int parcelCount;

    DeliverySystem(String systemName, String telegram) {
        couriers = new ArrayList<>();
        users = new ArrayList<>();
        this.systemName = systemName;
        this.telegram = telegram;
        parcelCount = 0;
    }

    public void addStaff(Courier courier) {
        couriers.add(courier);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addParcel(Parcel parcel) {
        parcels.add(parcel);
        parcelCount++;
    }


    public String getName(){
        return systemName;
    }

    public String getTele(){
        return telegram;
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

    // like getAvailableDriver()
    public Courier getAvailableCourier() {
        for (int i = 0; i < couriers.size(); i++) {
            if (couriers.get(i).isAvailable()) {
                return couriers.get(i);
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

        courier.setStatus(false);
        
        return request;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "DeliverySystem [systemName=" + systemName + ", couriers=" + Arrays.toString(couriers.toArray()) + ", users=" + Arrays.toString(users.toArray()) + ", parcels=" + Arrays.toString(parcels.toArray())
                + ", courierCount=" + Courier.courierCount + "]";
    }

    
}
