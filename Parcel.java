import java.util.UUID;
class Parcel {
    String parcelId;
    String type;
    double weight;
    double price;

    Parcel(String type, double weight , double price) {
        this.type = type;
        this.weight = weight;
        this.price = price;
        this.parcelId = UUID.randomUUID().toString();
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



    
    // double getPrice(){
    //     return price;
    // }

    // double getWeight(){
    //     return weight;
    // }
    // String getType(){
    //     return type;
    // }  
}
