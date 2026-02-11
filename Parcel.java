import java.util.UUID;
public class Parcel {
    public String parcelId;
    public String type;
    public double weight;
    public double price;

    Parcel(String type, double weight , double price) {
        this.type = type;
        this.weight = weight;
        this.price = price;
        this.parcelId = UUID.randomUUID().toString();
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

    @Override
    public String toString() {
        return "[type=" + type + ", weight=" + weight + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

        String getParcelId(){
        return parcelId;
    }

}
