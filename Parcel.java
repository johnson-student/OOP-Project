public class Parcel {
    String type;
    double weight;

    public Parcel(String type, double weight) {
        this.type = type;
        this.weight = weight;
    }
    public void print(){
        System.out.println("Items type: " + type);
        System.out.println("weight: " + weight + "kg");
    }
}
