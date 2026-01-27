public class Courier {
    String name;
    String phone;
    boolean available;

    public Courier(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.available = true; // free by default
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isAvailable() {
        return available;
    }

    public void assignDelivery() {
        this.available = false;
    }

    public void finishDelivery() {
        this.available = true;
    }
}
