import java.util.UUID;

public class Courier {
    private String id;
    private String name;
    private String phone;
    private boolean available;
    private double rating;

    Courier(String name, String phone, double rating) {
        if (name == null || phone == null) {
            return;
        }

        if (rating < 0 || rating > 5) {
            return;
        }

        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.available = true;
        this.rating = rating;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return available; }

    public void setDelivery(boolean available) {
        this.available = available;
    }

    public void setPhone(String newPhone){
        if(newPhone == null) return;
        this.phone = newPhone;
    }

    public void setRating(double newRating) {
        if (newRating < 0 || newRating > 5) {
            return ;
        }
        this.rating = newRating;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Courier)) return false;
        Courier other = (Courier) obj;
        return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Courier{id='" + id + "', name='" + name +
                "', available=" + available +
                ", rating=" + rating + "}";
    }
}
