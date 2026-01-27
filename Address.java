class Address {
    private String city;
    private String district;
    private String street;

    public Address(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }

    public String getAddress() {
        return street + ", " + district + ", " + city;
    }
}
