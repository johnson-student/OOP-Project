 class DeliveryRequest {
    String id;
    User sender;
    User receiver;
    Parcel parcel;
    Courier courier;
    double fee;

    DeliveryRequest(
            User sender,
            User receiver,
            Parcel parcel,
            Courier courier,
            double fee
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.parcel = parcel;
        this.courier = courier;
        this.fee = fee;
    }

     void printInfo() {
        System.out.println("=== Delivery Request ===");
        System.out.println("Sender: " + sender.getName());
        System.out.println("Receiver: " + receiver.getName());
        System.out.println("Courier: " + courier.getName());
        System.out.println("Fee: " + fee + "$");
        parcel.print();
    }
}
