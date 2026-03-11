package user;
public class Staff implements InStaff {
    private String staffIdString;
    private String userName;
    private String fullName;
    private String phone;
    private double rating;
    private String password;
    private boolean active = true;
    private float salary;
    public static int staffCount = 0;




    // Constructor
    public Staff(String fullname, String userName, String phone, String password,double rating , float salary) {
        this.staffIdString = String.valueOf(++staffCount);
        setPassword(password);
        setName(userName);
        setFullName(fullName);
        setPhone(phone);
        setRating(rating);
    }

    @Override
    public boolean can(String action) { return false;}

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    // Getters 
    protected String getPassword() { return password; }
    public String getStaffId() { return staffIdString; }
    public String getUsername() { return userName; }
    public String getPhone() { return phone; }
    public double getRating() { return rating; }
    public boolean isActive() { return active; }
    public String getFullname() {return fullName;}
    public float getSalary() {return salary;}

    // Setters
    public void setSalary(float salary){
        setSalary(salary);
    }
    public void setFullName(String fullName, String password){
        if(this.password.equals(password)){
            this.fullName = fullName;
        }else{
            System.out.println("Wrong Password!! try agian...");
        }
    }

    public void setFullName(String fullName){
        if(fullName.length() >= 8 || fullName.length() <= 16){
            this.fullName = fullName;
        }else{
            System.out.println("fill with the requestment length");
        }
    }
    
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


    public void setName(String userName, String password){
        if(this.password.equals(password)){
            this.userName = userName;
        }else{
            System.out.println("Wrong Password!! try agian...");
        }
    }
    public void setName(String userName){
        if(userName.length() >= 8 || userName.length() <= 16){
            this.userName = userName;
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
        Staff s1 = (Staff) obj;
        if(s1.phone.equals(phone))
        {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Staff{staffIdString='" + staffIdString + "', userName='" + userName +
                ", rating=" + rating + "}";
    }
}