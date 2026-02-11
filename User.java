import java.util.UUID;
public class User {
    private String userID;
    private String name;
    private String phone;
    private Address address;
    private boolean isMembership;
    private int useCount;

    User(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    User(String name, String phone, Address address, boolean isMembership) {
        userID = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.isMembership = isMembership;
        useCount = 0;
    }

    public String getID() { return userID; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }
    public boolean isMember() { return isMembership; }
    public int getUseCount() { return useCount; }

    public void setMembership(boolean isMembership){
        this.isMembership = isMembership;
    }

    public void increaseUseCount() {
        useCount++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return phone.equals(other.phone);
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', phone='" + phone +
                "', membership=" + membership +
                ", balance=" + balance +
                ", useCount=" + useCount + "}";
    }
}
