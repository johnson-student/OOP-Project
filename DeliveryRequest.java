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
        System.out.println("Sender: " + sender.name);
        System.out.println("Receiver: " + receiver.name);
        System.out.println("Courier: " + courier.name);
        System.out.println("Fee: " + fee + "$");
        System.out.println("parcel info: " + parcel.toString());
    }

     @Override
     public String toString() {
        return "DeliveryRequest [id=" + id + ", sender=" + sender + ", receiver=" + receiver + ", parcel=" + parcel
                + ", courier=" + courier + ", fee=" + fee + "]";
     }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
   
}
