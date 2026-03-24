package other;
import user.Courier;
import user.User;

public class DeliveryRequest {
    private String id;
    private User sender;
    private User receiver;
    private Parcel parcel;
    private Courier courier;
    private double fee;
    public static int requestCount = 0;

   public DeliveryRequest(
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
    public User getUser(){return sender;}
    public User getReciever(){return receiver;}
    public Courier getCourier(){return courier;}
    public Parcel getParcel(){return parcel;}
    
    public void printInfo() {
        System.out.println("=== Delivery Request ===");
        System.out.println("Sender: " + sender.getName());
        System.out.println("Receiver: " + receiver.getName());
        System.out.println("Courier: " + courier.getUsername());
        System.out.println("Fee: " + fee + "$");
        System.out.println("parcel info: " + parcel.toString());
    }

     @Override
     public String toString() {
           return "\n===== DELIVERY REQUEST =====" +
           "\nRequest ID : " + id +
           "\n\n--- Sender ---" +
           "\n" + sender +
           "\n\n--- Receiver ---" +
           "\n" + receiver +
           "\n\n--- Courier ---" +
           "\nUsername : " + courier.getUsername() +
           "\n\n--- Parcel ---" +
           "\n" + parcel +
           "\n\n--- Fee ---" +
           "\n$" + fee +
           "\n============================\n";
     }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
   
}
