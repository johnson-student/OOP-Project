class Parcel {
    String type;
    double weight;

    Parcel(String type, double weight) {
        this.type = type;
        this.weight = weight;
    }
    double getWeight(){
        return weight;
    }
    String getType(){
        return type;
    }
    void print(){
        System.out.println("Items type: " + type);
        System.out.println("weight: " + weight + "kg");
    }
}
