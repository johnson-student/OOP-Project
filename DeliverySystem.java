import java.util.Arrays;

public class DeliverySystem {
    private String systemName;
    private Courier[] couriers;
    private int courierCount;

    DeliverySystem(String systemName,int maxCouriers) {
        couriers = new Courier[maxCouriers];
        this.systemName = systemName;
        courierCount = 0;
    }

    public void addCourier(Courier courier) {
        if (courierCount < couriers.length) {
            couriers[courierCount] = courier;
            courierCount++;
        } else {
            System.out.println("Courier list is full.");
        }
    }


    public String getName(){
        return systemName;
    }

    // like getAvailableDriver()
    public Courier getAvailableCourier() {
        for (int i = 0; i < courierCount; i++) {
            if (couriers[i].isAvailable()) {
                return couriers[i];
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

        if (courier == null) {
            System.out.println("No courier available.");
            return null;
        }
        double fee = PricingService.calculateFee(parcel);

        DeliveryRequest request = new DeliveryRequest(sender, receiver, parcel, courier , fee);

        courier.setStatus(false);
        
        return request;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "DeliverySystem [systemName=" + systemName + ", couriers=" + Arrays.toString(couriers)
                + ", courierCount=" + courierCount + "]";
    }

    
}
