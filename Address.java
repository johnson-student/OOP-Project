class Address {
    String city;
    String district;
    String street;

    Address(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }

    String getCity() {
        return city;
    }
    String getDistrict() {
        return district;
    }
    String getStreet() {
        return street;
    }
    String getFullAddress(){
        return city + "," + district + "," + street ;
    }
}
