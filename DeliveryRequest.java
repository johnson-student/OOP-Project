public class DeliveryRequest {
    private String id;
    private User sender;
    private User receiver;
    private Parcel parcel;
    private Courier courier;
    private double fee;
    public static int requestCount = 0;

    DeliveryRequest(
            User sender,
            User receiver,
            Parcel parcel,
            Courier courier,
            double fee
    ) {
        this.id =String.valueOf(requestCount++);
        this.sender = sender;
        this.receiver = receiver;
        this.parcel = parcel;
        this.courier = courier;
        this.fee = fee;
    }
    
    public String getId() { return id; }
    
    public void printInfo() {
        System.out.println("=== Delivery Request ===");
        System.out.println("Sender: " + sender.getName());
        System.out.println("Receiver: " + receiver.getName());
        System.out.println("Courier: " + courier.getName());
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
