class Address {
    String city;
    String district;
    String street;
    
    Address(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }
    
    //getter
    String getCity() {
        return city;
    }
    String getDistrict() {
        return district;
    }
    String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", district=" + district + ", street=" + street + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    





}
