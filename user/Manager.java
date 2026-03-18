package user;
public class Manager extends Staff {
    private double bonus;
    // Constructor
    public Manager(String fullName,String username, String phone, String password, double rating , float salary) {
        super(fullName, username, phone, password, rating, salary);
        setBonus(0);
    }

    // getter setter
    public double getBonus() {
        return bonus;
    }
    

    public void setBonus(double bonus) {
        if (bonus < 0) {
            System.out.println("Bonus cannot be negative.");
            return;
        }
        this.bonus = bonus;
    }

    @Override
    public void setSalary(float salary) {
        if( salary < 500 )
        {
            System.out.println("error: need more salary");
        }else
        {
            super.setSalary(salary);
        }
    }

    public boolean approveLeave(Staff staff, int days){
        System.out.println("Leave approved for " + staff.getUsername() + " for " + days + " days.");
        return true;
    }

    @Override
    public boolean can(String action) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Manager)) return false;
        Manager other = (Manager) obj;
        if (!super.equals(other)) 
        {
             return false; 
        }else if(Double.doubleToLongBits(bonus) != Double.doubleToLongBits(other.bonus))
        { 
            return false;
        }  
        return true;
    }



    // @Override
    // public String toString() {
    //     return "Manager{staffId='" + getStaffId() + "', name='" + getUsername() +
    //             ", rating=" + getRating() + "}";
    // }
}
    // public boolean checkPassword(String input) {
    //     return password != null && password.equals(input);
    // }

    // // Getters 
    // public String getStaffId() { return staffId; }
    // public String getUsername() { return name; }
    // public String getPhone() { return phone; }
    // public double getRating() { return rating; }
    // public String getPosition() { return position; }
    // public boolean isActive() {
    //     return true;
    // }
    // public String getFullname() {
    //     return name;
    //  }

    // // Setters
    // public void setPassword(String newPassword) {
    //     // regex
    //     String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    //     if(newPassword.matches(regex) && newPassword.length() >= 8) {
    //         this.password = newPassword;
    //     } else {
    //         System.out.println("Password must be at least 8 characters long and include a mix of uppercase letters, lowercase letters, numbers, and special characters.");
    //     }
    // }

    // public void setPassword(String newPassword, String oldPassword) {
    //     // regex
    //     String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    //     if(newPassword.matches(regex) && newPassword.length() >= 8) {
    //         if(this.password.equals(oldPassword)) {
    //             this.password = newPassword;
    //         } else {
    //             System.out.println("Incorrect old password. Password not updated.");
    //         }
    //     } else {
    //         System.out.println("Password must be at least 8 characters long and include a mix of uppercase letters, lowercase letters, numbers, and special characters.");
    //     }
    // }

    // public void setPosition(String position) {
    //     this.position = position;
    // }

    // public void setName(String name, String password){
    //     if(this.password.equals(password)){
    //         this.name = name;
    //     }else{
    //         System.out.println("Wrong Password!! try agian...");
    //     }
    // }
    // public void setName(String name){
    //     if(name.length() >= 8 || name.length() <= 16){
    //         this.name = name;
    //     }else{
    //         System.out.println("fill with the requestment length");
    //     }
    // }

    // public void setPhone(String phone) {
    //     String p = (phone == null) ? "" : phone.trim();
    //     // simple validation: only digits, length 8–15
    //     if (!isDigits(p) || p.length() < 8 || p.length() > 15) this.phone = "00000000";
    //     else this.phone = p;
    // }

    // public void setRating(double newRating) {
    //     if (newRating < 0 || newRating > 5) {
    //         return ;
    //     }
    //     this.rating = newRating;
    // }

    // private boolean isBlank(String s) {
    //     return s == null || s.trim().isEmpty();
    // }

    // private boolean isDigits(String s) {
    //     if (isBlank(s)) return false;
    //     for (int i = 0; i < s.length(); i++) {
    //         char c = s.charAt(i);
    //         if (c < '0' || c > '9') return false;
    //     }
    //     return true;
    // }

