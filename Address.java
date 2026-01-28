class Address {
    String city;
    String district;
    String street;

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
    public String getFullAddress(){
        return city + "," + district + "," + street ;
    }
}
