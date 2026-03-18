package other;
public class Address {
    private String city;
    private String district;
    private String street;
    
    public Address(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }
    
    //getter
    public String getCity() {
        return city;
    }
    public String getDistrict() {
        return district;
    }
    public String getStreet() {
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
