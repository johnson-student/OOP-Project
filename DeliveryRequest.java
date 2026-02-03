 class DeliveryRequest {
    String id;
    User sender;
    User receiver;
    Parcel parcel;
    Courier courier;

     DeliveryRequest(
            User sender,
            User receiver,
            Parcel parcel,
            Courier courier
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.parcel = parcel;
        this.courier = courier;
    }

     void printInfo() {
        System.out.println("=== Delivery Request ===");
        System.out.println("Sender: " + sender.getName());
        System.out.println("Receiver: " + receiver.getName());
        System.out.println("Courier: " + courier.getName());
        parcel.print();
    }
}
