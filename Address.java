class Address {
    private String city;
    private String district;
    private String street;

    public Address(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public String getDistrict() {
        return district;
    }
    public String getStreet() {
        return street;
    }
    public String getAddress(){
        return city + "," + district + "," + street ;
    }
}
