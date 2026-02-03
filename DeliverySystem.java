class DeliverySystem {
    String systemName;
    Courier[] couriers;
    int courierCount;

    DeliverySystem(String systemName,int maxCouriers) {
        couriers = new Courier[maxCouriers];
        this.systemName = systemName;
        courierCount = 0;
    }

    void addCourier(Courier courier) {
        if (courierCount < couriers.length) {
            couriers[courierCount] = courier;
            courierCount++;
        } else {
            System.out.println("Courier list is full.");
        }
    }


    String getName(){
        return systemName;
    }

    // like getAvailableDriver()
    Courier getAvailableCourier() {
        for (int i = 0; i < courierCount; i++) {
            if (couriers[i].isAvailable()) {
                return couriers[i];
            }
        }
        return null;
    }

    DeliveryRequest createRequest(
            User sender,
            User receiver,
            Parcel parcel
    ) {
        Courier courier = getAvailableCourier();

        if (courier == null) {
            System.out.println("No courier available.");
            return null;
        }

        DeliveryRequest request =
                new DeliveryRequest(sender, receiver, parcel, courier);

        courier.assignDelivery();
        
        return request;
    }
}
