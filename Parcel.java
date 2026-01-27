public class Parcel {
    String type;
    double weight;

    public Parcel(String type, double weight) {
        this.type = type;
        this.weight = weight;
    }
    public void print(){
        system.out.println("Items type: " + type);
        system.out.println("weight: " + weight + "kg");
    }
}
