class Courier {
    String id;
    String name;
    String phone;
    boolean available;

    Courier(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.available = true; // free by default
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

    void finishDelivery() {
        this.available = true;
    }
}
