package user;
public interface InStaff {
    String getStaffId();
    String getUsername();
    boolean isActive();
    boolean checkPassword(String input);
    String getFullname();
    float getSalary();
    boolean can(String action);
}
