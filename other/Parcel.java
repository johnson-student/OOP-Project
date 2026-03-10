package other;
import java.util.UUID;
public class Parcel {
    private String senderID;
    private String parcelId;
    private String type;
    private double weight;
    private int qtt;
    private double price;

    Parcel(String type, double weight , int qtt, double price, String senderID) {
        this.type = type;
        this.weight = weight;
        this.qtt = qtt;
        this.price = price;
        this.parcelId = UUID.randomUUID().toString();
        this.senderID = senderID;
    }

    public double getPrice(){
        return price;
    }

    public double getWeight(){
        return weight;
    }
    public String getType(){
        return type;
    }  
    public int getQtt(){
        return qtt;
    }

    @Override
    public String toString() {
        return "[type=" + type + ", weight=" + weight + ", ParcelID =  "+ parcelId+ ", SenderID = "+senderID +"]";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

        String getParcelId(){
        return parcelId;
    }

}
