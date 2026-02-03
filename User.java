class User {
    String name;
    String phone;
    Address address;

    User(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    String getName() {
        return name;
    }

    String getPhone() {
        return phone;
    }

    String getAddress() {
        return address.getFullAddress();
    }
}
