package other;

public class Address {
    private String city;
    private String district;
    private String street;

    public Address(String city, String district, String street) {
        setCity(city);
        setDistrict(district);
        setStreet(street);
    }

    // getter
    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    // setter
    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", district=" + district + ", street=" + street + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true; // same object

        Address other = (Address) obj;

        return city.equalsIgnoreCase(other.city)
                && district.equalsIgnoreCase(other.district)
                && street.equalsIgnoreCase(other.street);
    }

}
