import java.util.UUID;
class Courier {
    String id;
    String name;
    String phone;
    boolean available;
    double rating;

    Courier(String name, String phone , double rating) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.available = true; // free by default
        this.rating = rating;
        
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Courier{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", available=").append(available);
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    } 

    //getter
    double getRating(){
        return rating;
    }

    String getName() {
        return name;
    }

    String getPhone() {
        return phone;
    }

    boolean isAvailable() {
        return available;
    }

    void assignDelivery() {
        this.available = false;
    }

    // void finishDelivery() {
    //     this.available = true;
    // }
  
}
