package user;
public class Staff implements InStaff {
    private String staffIdString;
    private String name;
    private String phone;
    private double rating;
    private String password;
    private String position; 
    boolean active = true;
    public static int staffCount = 0;

    // Constructor
    public Staff(String name, String phone, String password, String position,double rating) {
        this.staffIdString = String.valueOf(++staffCount);
        setPassword(password);
        setName(name);
        setPhone(phone);
        setPosition(position);
        setRating(rating);
    }

  @Override
    public boolean can(String action) {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    // Getters 
    public String getPassword() { return password; }
    public String getStaffId() { return staffIdString; }
    public String getUsername() { return name; }
    public String getPhone() { return phone; }
    public double getRating() { return rating; }
    public String getPosition() { return position; }
    public boolean isActive() { return active; }
    public String getFullname() {
        return name;
     }

    // Setters
    public void setPassword(String newPassword) {
        // regex
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if(newPassword.matches(regex) && newPassword.length() >= 8) {
            this.password = newPassword;
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

    public void setPosition(String position) {
        this.position = position;
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

    public void setRating(double newRating) {
        if (newRating < 0 || newRating > 5) {
            return ;
        }
        this.rating = newRating;
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
        if (!(obj instanceof Staff)) return false;
        Staff other = (Staff) obj;
        return staffIdString.equals(other.staffIdString);
    }

    @Override
    public String toString() {
        return "Staff{staffIdString='" + staffIdString + "', name='" + name +
                ", rating=" + rating + "}";
    }
}