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
    private Boolean isCompleted;
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
        isCompleted = false;
    }
    
    // getter
    public String getId() { return id; }
    public User getUser(){return sender;}
    public User getReciever(){return receiver;}
    public Courier getCourier(){return courier;}
    public Parcel getParcel(){return parcel;}
    public double getFee(){return fee;}
    public Boolean getIsCompleted(){return isCompleted;}

    // setter
    public void setIsCompleted(Boolean isCompleted){this.isCompleted = isCompleted;}
    
    public void printInfo() {
        System.out.println("=== Delivery Request ===");
        System.out.println("Request ID: " + id +" ("+ (isCompleted ? "Completed" : "pending"));
        System.out.println("Sender: " + sender.getName());
        System.out.println("Receiver: " + receiver.getName());
        System.out.println("Courier: " + courier.getFullname());
        System.out.println("Fee: " + fee + "$");
        System.out.println("parcel info: " + parcel.toString());
    }

     @Override
     public String toString() {
           return "\n===== DELIVERY REQUEST =====" +
           "\nRequest ID : " + id + " ("+ (isCompleted ? "Completed" : "pending")+
           "\n\n--- Sender ---" +
           "\n" + sender +
           "\n\n--- Receiver ---" +
           "\n" + receiver + 
           "\n"+receiver.getAddress() + 
           "\n\n--- Courier ---" +
           "\nUsername : " + courier.getFullname() +
           "\n\n--- Fee ---" +
           "\n$" + fee +
           "\n\n--- Parcel ---" +
           "\n" + parcel +
           "\n============================\n";
     }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
   
}
