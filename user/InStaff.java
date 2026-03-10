package user;
public interface InStaff {
    String getStaffId();
    String getUsername();
    String getPosition();
    boolean isActive();
    boolean checkPassword(String input);
    String getFullname();
    boolean can(String action);
}
