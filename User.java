class User {
    private String name;
    private String phone;
    private Address address;
    private boolean isMembership;
    private double balance;
    private int useCount;

    User(String name, String phone, Address address, double balance, boolean isMembership) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.isMembership = isMembership;
        this.useCount = 0;
    }

    String getName() { return name; }
    String getPhone() { return phone; }
    Address getAddress() { return address; }
    double getBalance() { return balance; }
    boolean isMember() { return isMembership; }
    int getUseCount() { return useCount; }

    void setMembership(boolean isMembership){
        this.isMembership = isMembership;
    }

    void increaseUseCount() {
        useCount++;
    }

    boolean deductBalance(double amount) {
        if (balance < amount) return false;
        balance -= amount;
        increaseUseCount();
        return true;
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
