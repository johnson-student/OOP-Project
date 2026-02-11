public class Courier {
    private String id;
    private String name;
    private String phone;
    private boolean available;
    private double rating;
    private static int courierCount = 0;
    Courier(String name, String phone, double rating) {
        this.id = String.valueOf(++courierCount);
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

    public void setStatus(boolean available) {
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
