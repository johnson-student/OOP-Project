public class User {
    private String userID;
    private String name;
    private String phone;
    private Address address;
    private boolean isMembership;
    private String password;
    private int useCount;
    private static  int userCount = 0;

    User(String name, String phone, Address address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    User(String name, String phone, String password, Address address, boolean isMembership) {
        userID = String.valueOf(++userCount);
        this.name = name;
        this.phone = phone;
        this.password = password;
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
    public void setname(String name, String password){
        if(this.password.equals(password)){
            this.name = name;
        }else{
            System.out.println("Wrong Password!! try agian...");
        }
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
                "', membership=" + isMembership +
                ", useCount=" + useCount + "}";
    }
}
