package user;
import other.Address;

public class User {
    private String userID;
    private String name;
    private String phone;
    private Address address;
    private boolean isMembership;
    private boolean active;
    private String password;
    private int useCount;
    static  int userCount = 0;

    public User(String name, String phone, Address address) {
        setName(name);
        setPhone(phone);
        this.address = address;
    }

    public User(String name, String phone, String password, boolean isMembership) {
        userID = String.valueOf(++userCount);
        setName(name);
        setPhone(phone);
        setPassword(password);
        setMembership(isMembership);
        useCount = 0;
    }
    
    public void increaseUseCount() {
        useCount++;
    }
    // Getters
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }
    public boolean isMember() { return isMembership; }
    public int getUseCount() { return useCount; }
    public boolean isActive() {return active;}

    // Setters
    public void setMembership(boolean isMembership){
        this.isMembership = isMembership;
    }

    public void setActivec(boolean active){
        this.active = active;
    }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    public void setPassword(String Password) {
        // regex
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if(Password.matches(regex) && Password.length() >= 8) {
            this.password = Password;
        } else {
            System.out.println("Password must be at least 8 characters long and include a mix of uppercase letters, lowercase letters, numbers, and special characters.");
        }
    }

    public void setPassword(String newPassword, String oldPassword) {
        // regex
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if(newPassword.matches(regex) && newPassword.length() >= 8) {
            if(this.password.equals(oldPassword)) {
                this.password = newPassword;
            } else {
                System.out.println("Incorrect old password. Password not updated.");
            }
        } else {
            System.out.println("Password must be at least 8 characters long and include a mix of uppercase letters, lowercase letters, numbers, and special characters.");
        }
    }

    public void setName(String name, String password){
        if(this.password.equals(password)){
            this.name = name;
        }else{
            System.out.println("Wrong Password!! try agian...");
        }
    }
    public void setName(String name){
        if(name.length() >= 8 || name.length() <= 16){
            this.name = name;
        }else{
            System.out.println("fill with the requestment length");
        }
    }

    public void setPhone(String phone) {
        String p = (phone == null) ? "" : phone.trim();
        // simple validation: only digits, length 8–15
        if (!isDigits(p) || p.length() < 8 || p.length() > 15) this.phone = "00000000";
        else this.phone = p;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isDigits(String s) {
        if (isBlank(s)) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
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
                "', membership=" + isMembership +
                ", useCount=" + useCount + "}";
    }
}
